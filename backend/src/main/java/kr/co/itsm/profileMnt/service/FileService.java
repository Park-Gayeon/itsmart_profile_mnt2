package kr.co.itsm.profileMnt.service;

import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;

import java.util.List;

public interface FileService {
    List<TbAttachmentInfoDto> getFileList(String userId, String fileSe);

    TbAttachmentInfoDto getFile(String userId, String fileSe);
}
