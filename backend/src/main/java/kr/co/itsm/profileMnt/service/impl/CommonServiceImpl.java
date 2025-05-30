package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.dao.CommonDAO;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfoId;
import kr.co.itsm.profileMnt.dto.CodeNmRequest;
import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;
import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;
import kr.co.itsm.profileMnt.repository.TbAttachmentInfoRepository;
import kr.co.itsm.profileMnt.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonServiceImpl implements CommonService {
    private final CommonDAO commonDAO;
    private final TbAttachmentInfoRepository tbAttachmentInfoRepository;

    @Value("${file.upload-photo-dir}")
    private String uploadPhotoDir;

    @Value("${file.upload-excel-dir}")
    private String uploadExcelDir;

    @Override
    public int getNextSeq(String userId) {
        return commonDAO.getNextSeq(userId);
    }

    @Override
    public TbAttachmentInfoDto saveImageFile(MultipartFile file) {
        log.info("파일 저장을 시작합니다");
        TbAttachmentInfoDto tempFile = null;
        try {
            tempFile = new TbAttachmentInfoDto();
            // 파일 확인
            if (!file.isEmpty()) {
                String oriFileNm = file.getOriginalFilename();
                String extension = oriFileNm.substring(oriFileNm.lastIndexOf(".") + 1);
                String uploadDir = "";
                log.info("oriFileNm={}, extension={}", oriFileNm, extension);

                // 파일 저장 경로 생성
                String sverFileNm = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;

                if(extension.contains("xlsx") || extension.contains("xls")){
                    uploadDir = uploadExcelDir;
                } else {
                    uploadDir = uploadPhotoDir;
                }

                File uploadPath = new File(uploadDir, sverFileNm);
                log.info("uploadPath={}", uploadPath);

                // 서버에 파일 저장
                file.transferTo(uploadPath);
                log.info("파일을 저장했습니다.");

                // 객체에 데이터 setting
                tempFile.setFileOriNm(oriFileNm);
                tempFile.setFileSverNm(sverFileNm);
                tempFile.setFileSverPath(uploadDir + "/" + sverFileNm);
                tempFile.setFileExtension(extension);

            }
        } catch (Exception e) {
            log.debug("파일 저장 실패: ", e.getMessage());
        }
        return tempFile;
    }

    @Override
    @Transactional
    public int insertUsrFileInfo(TbAttachmentInfoDto file) {
        int file_seq = getNextSeq(file.getUserId());
        try {
            TbAttachmentInfo tbAttachment = TbAttachmentInfo.builder()
                    .id(TbAttachmentInfoId.builder()
                            .fileSeq(file_seq)
                            .fileSe("PROFILE")
                            .userId(file.getUserId())
                            .build())
                    .fileOriNm(file.getFileOriNm())
                    .fileSverNm(file.getFileSverNm())
                    .fileSverPath(file.getFileSverPath())
                    .fileExtension(file.getFileExtension())
                    .useYn('Y') // 기본값 설정
                    .createdDate(Instant.now())
                    .creator(file.getUserId())
                    .build();
            log.info("파일 정보를 저장했습니다.");
            tbAttachmentInfoRepository.save(tbAttachment);

        } catch (Exception e){
            log.debug("파일 정보 저장 실패: user_id={}, file_seq={}", file.getUserId(), file.getFileSeq(), e.getMessage());
        }
        return file_seq;
    }

    @Override
    public List<TbCommonCodeDto> getCodeList(Map<String, String> params) {
        return commonDAO.getCodeList(params);
    }

    @Override
    public Map<String, String> getCodeNames(List<CodeNmRequest> request){
        Map<String, String> result = new HashMap<>();

        for (CodeNmRequest req : request) {
            String key = req.codeGroupId() + ":" + req.codeId();
            String value = commonDAO.codeValue(req.codeGroupId(), req.codeId());
            result.put(key, value != null ? value : "");
        }
        return result;
    }

}

