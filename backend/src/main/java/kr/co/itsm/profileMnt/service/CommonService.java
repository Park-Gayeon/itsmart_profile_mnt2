package kr.co.itsm.profileMnt.service;

import kr.co.itsm.profileMnt.dto.CodeNmRequest;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;

import java.util.List;
import java.util.Map;

public interface CommonService {

    List<TbCommonCodeDto> getCodeList(Map<String, String> params);

    Map<String, String> getCodeNames(List<CodeNmRequest> request);
}
