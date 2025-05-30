package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
import kr.co.itsm.profileMnt.repository.TbAttachmentInfoRepository;
import kr.co.itsm.profileMnt.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final TbAttachmentInfoRepository tbAttachmentInfoRepository;

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
}
