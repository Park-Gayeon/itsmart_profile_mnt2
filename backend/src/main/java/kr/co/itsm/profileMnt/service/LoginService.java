package kr.co.itsm.profileMnt.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.itsm.profileMnt.dto.AuthRequest;
import kr.co.itsm.profileMnt.dto.AuthResponse;
import kr.co.itsm.profileMnt.dto.LoginDto;

import java.util.Map;

public interface LoginService {
    AuthResponse authenticate(LoginDto login);

    AuthResponse refreshToken(String refreshToken);

    void changeUsrPassword(AuthRequest login, String flag);

    void deleteUsr(String userId);
}
