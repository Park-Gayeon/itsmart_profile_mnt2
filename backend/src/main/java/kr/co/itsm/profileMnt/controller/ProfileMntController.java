package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
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
    private final CommonService commonService;

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
        int fileSeq = 0;
        if (file != null) {
            // file upload to server
            TbAttachmentInfoDto tbAttachmentInfo = commonService.saveImageFile(file);

            // 파일 정보 DB 저장
            tbAttachmentInfo.setUserId(profile.getUserId());
            fileSeq = commonService.insertUsrFileInfo(tbAttachmentInfo);
        }
        profile.setFileSeq(fileSeq);
        profileMntService.insertUsrProfile(profile);

        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Object>>> getDetailInfo(@PathVariable("userId") String userId) {
        log.info("사용자 프로필 조회");
        return ResponseEntity.ok(ApiResponse.success(profileMntService.getUsrProfileDetail(userId)));
    }



    @PutMapping("/{user_id}")
    public ResponseEntity<ApiResponse<Void>> saveUsrProfile(@PathVariable("user_id") String user_id,
                                                   @RequestPart TbUserProfileInfoDto profile,
                                                   @RequestParam(required = false, value = "imgFile") MultipartFile file) {
        log.info("사용자 프로필 수정");

        int fileSeq = 0;
        if (file != null) {
            // file upload to server
            TbAttachmentInfoDto tbAttachmentInfo = commonService.saveImageFile(file);

            // 파일 정보 DB 저장
            tbAttachmentInfo.setUserId(profile.getUserId());
            fileSeq = commonService.insertUsrFileInfo(tbAttachmentInfo);
        }
        profile.setFileSeq(fileSeq);
        profileMntService.updateUsrProfile(profile);

        return ResponseEntity.ok(ApiResponse.success());
    }
}
