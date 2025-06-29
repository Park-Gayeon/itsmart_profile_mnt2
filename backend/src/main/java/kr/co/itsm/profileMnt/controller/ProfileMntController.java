package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.dto.ProfileRequestDto;
import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;
import kr.co.itsm.profileMnt.service.*;
import kr.co.itsm.profileMnt.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileMntController {
    private final ProfileMntService profileMntService;
    private final FileService fileService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TbUserProfileInfoDto>>> getUsrProfileInfoList() {
        // TODO : User, 검색조건, Paging 파라미터 필요
        log.info("직원 프로필 목록 조회");
        List<TbUserProfileInfoDto> profileList = profileMntService.getUsrProfileInfoList();
        return ResponseEntity.ok(ApiResponse.success(profileList));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUserInfo(@RequestPart TbUserProfileInfoDto profile,
                                                              @RequestParam(required = false, value = "imgFile") MultipartFile file) {
        log.info("직원 프로필 등록");
        profileMntService.insertUsrProfile(profile, file);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<ProfileRequestDto>> getDetailInfo(@PathVariable("userId") String userId) {
        log.info("사용자 프로필 조회");
        ProfileRequestDto info = profileMntService.getUsrProfileDetail(userId);
        return ResponseEntity.ok(ApiResponse.success(info));
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse<Void>> saveUsrProfile(@RequestPart("payload") ProfileRequestDto payload,
                                                            @RequestPart(value = "imgFile", required = false) MultipartFile file){
        log.info("사용자 프로필 수정");
        profileMntService.setUsrProfileDetail(payload, file);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
