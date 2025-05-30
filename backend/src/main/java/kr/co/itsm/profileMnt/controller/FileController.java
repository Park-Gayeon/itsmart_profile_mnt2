package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.dto.TbAttachmentInfoDto;
import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;
import kr.co.itsm.profileMnt.service.FileService;
import kr.co.itsm.profileMnt.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/excel/{userId}")
    public ResponseEntity<ApiResponse<List<TbAttachmentInfoDto>>> getExcelFileList(@PathVariable("userId") String userId) {
        String fileSe = "EXCEL";

        return ResponseEntity.ok(ApiResponse.success(fileService.getFileList(userId, fileSe)));
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<TbAttachmentInfoDto>> getProFile(@PathVariable("userId") String userId) {
        String fileSe = "PROFILE";

        return ResponseEntity.ok(ApiResponse.success(fileService.getFile(userId, fileSe)));
    }
}
