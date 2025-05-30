package kr.co.itsm.profileMnt.service;

import kr.co.itsm.profileMnt.dto.CodeNmRequest;
import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CommonService {
    int getNextSeq(String userId);

    TbAttachmentInfoDto saveImageFile(MultipartFile file);

    int insertUsrFileInfo(TbAttachmentInfoDto file);

    List<TbCommonCodeDto> getCodeList(Map<String, String> params);

    Map<String, String> getCodeNames(List<CodeNmRequest> request);
}
