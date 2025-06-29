package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfoId;
import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
import kr.co.itsm.profileMnt.repository.TbAttachmentInfoRepository;
import kr.co.itsm.profileMnt.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final TbAttachmentInfoRepository tbAttachmentInfoRepository;

    @Value("${file.upload-photo-dir}")
    private String uploadPhotoDir;

    @Value("${file.upload-excel-dir}")
    private String uploadExcelDir;

    @Override
    public List<TbAttachmentInfoDto> getFileList(String userId, String fileSe) {

        List<TbAttachmentInfo> attachmentInfoList = tbAttachmentInfoRepository.findByIdUserIdAndIdFileSeOrderByIdFileSeqDesc(userId, fileSe);

        if (attachmentInfoList.isEmpty()) {
            throw new NoSuchElementException("Attachments not found for userId: " + userId);
        }
        return attachmentInfoList.stream()
                .map(TbAttachmentInfoDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TbAttachmentInfoDto getFile(String userId, String fileSe) {
        return tbAttachmentInfoRepository.findTopByUserIdAndFileSe(userId, fileSe)
                .map(TbAttachmentInfoDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("No attachment found for userId: " + userId));
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

                // 디버깅용 코드 추가
                log.info("실제 저장된 파일 존재 여부: {}", uploadPath.exists());
                log.info("실제 저장된 파일 절대경로: {}", uploadPath.getAbsolutePath());
                log.info("실제 저장된 파일 크기: {}", uploadPath.length());

                // 현재 작업 디렉토리 확인
                log.info("현재 작업 디렉토리: {}", System.getProperty("user.dir"));

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
        int file_seq = tbAttachmentInfoRepository.getNextSeq(file.getUserId());
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

}
