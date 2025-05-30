package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.domain.TbCommonCode;
import kr.co.itsm.profileMnt.dto.CodeNmRequest;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;
import kr.co.itsm.profileMnt.service.CommonService;
import kr.co.itsm.profileMnt.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/code/{codeId}")
    public ResponseEntity<ApiResponse<List<TbCommonCodeDto>>> code(@PathVariable("codeId") String codeId,
                                            @RequestParam(required = false, value = "type") String type) {
        log.info("code 조회 : {}", codeId);
        log.info("type 조회 : {}", type);
        Map<String, String> params = new HashMap<>();
        params.put("codeId", codeId);
        params.put("type", type);

        return ResponseEntity.ok(ApiResponse.success(commonService.getCodeList(params)));
    }
    @PostMapping("/codeNm")
    public ResponseEntity<ApiResponse<Map<String, String>>> codeNames(@RequestBody List<CodeNmRequest> request) {
        return ResponseEntity.ok(ApiResponse.success(commonService.getCodeNames(request)));
    }

}
