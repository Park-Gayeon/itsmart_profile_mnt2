package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.service.ProfileMntService;
import kr.co.itsm.profileMnt.service.ProjectService;
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
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/calc/{userId}")
    public ResponseEntity<ApiResponse<Integer>> calcTotalMonth(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(projectService.calcTotalMonth(userId)));
    }
}
