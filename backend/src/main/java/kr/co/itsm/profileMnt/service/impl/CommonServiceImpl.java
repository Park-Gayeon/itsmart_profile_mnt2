package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.dao.CommonDAO;
import kr.co.itsm.profileMnt.dto.CodeNmRequest;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;
import kr.co.itsm.profileMnt.repository.TbAttachmentInfoRepository;
import kr.co.itsm.profileMnt.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonServiceImpl implements CommonService {
    private final CommonDAO commonDAO;
    private final TbAttachmentInfoRepository tbAttachmentInfoRepository;

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

