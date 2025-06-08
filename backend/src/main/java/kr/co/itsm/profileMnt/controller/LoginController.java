package kr.co.itsm.profileMnt.controller;

import kr.co.itsm.profileMnt.dto.AuthRequest;
import kr.co.itsm.profileMnt.dto.AuthResponse;
import kr.co.itsm.profileMnt.dto.LoginDto;
import kr.co.itsm.profileMnt.service.LoginService;
import kr.co.itsm.profileMnt.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(@RequestBody AuthRequest login) {
        LoginDto user = new LoginDto();
        user.setUserId(login.userId());
        user.setUserPw(login.userPw());

        return ResponseEntity.ok(ApiResponse.success(loginService.authenticate(user)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        return ResponseEntity.ok(ApiResponse.success(loginService.refreshToken(refreshToken)));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Stateless 환경에서는 클라이언트에서 토큰을 삭제하도록 안내
        return ResponseEntity.ok("Please remove tokens from client storage");
    }

    @PostMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody AuthRequest login) {
        loginService.changeUsrPassword(login);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
