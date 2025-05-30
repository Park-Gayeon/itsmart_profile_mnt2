package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.service.WorkExperienceService;
import kr.co.itsm.profileMnt.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work")
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;

    @GetMapping("/calc/{userId}")
    public ResponseEntity<ApiResponse<Integer>> calcTotalMonth(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(workExperienceService.calcTotalMonth(userId)));
    }
}
