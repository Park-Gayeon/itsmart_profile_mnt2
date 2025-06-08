package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.domain.TbUserProfileInfo;
import kr.co.itsm.profileMnt.dto.AuthRequest;
import kr.co.itsm.profileMnt.dto.AuthResponse;
import kr.co.itsm.profileMnt.dto.LoginDto;
import kr.co.itsm.profileMnt.repository.TbUserProfileInfoRepository;
import kr.co.itsm.profileMnt.service.LoginService;
import kr.co.itsm.profileMnt.util.handler.CustomException;
import kr.co.itsm.profileMnt.util.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final TbUserProfileInfoRepository profileRepository;

    @Override
    public AuthResponse authenticate(LoginDto loginRequest) {
        log.info("[Login] user_id : {}", loginRequest.getUserId());
        try {
            // [인증]
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getUserPw())
            );
            // 인증된 사용자 정보 확인
            LoginDto authenticatedUser = (LoginDto) authentication.getPrincipal();
            log.info("[LoginServiceImpl] authorities: {}", authenticatedUser.getAuthorities());

            // [발급] access token, refresh token
            String accessToken = jwtUtils.generateAccessToken(authenticatedUser);
            String refreshToken = jwtUtils.generateRefreshToken(authenticatedUser);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .username(authenticatedUser.getUsername())
                    .authorities(authenticatedUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build();

        } catch (UsernameNotFoundException e) {
            log.error("[authenticationManager.authenticate] 사용자를 찾을 수 없습니다.");
            throw new CustomException("사용자를 찾을 수 없습니다");
        } catch (BadCredentialsException e) {
            log.error("[authenticationManager.authenticate] 비밀번호가 일치하지 않습니다");
            throw new CustomException("비밀번호가 일치하지 않습니다");
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            throw new CustomException("유효하지 않은 리프레시 토큰입니다");
        }

        String username = jwtUtils.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtils.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .username(username)
                .authorities(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void changeUsrPassword(AuthRequest login) {
        String encodedPassword = passwordEncoder.encode(login.userPw());
        profileRepository.updatePassword(login.userId(), encodedPassword);
    }

    @Override
    public void deleteUsr(String userId) {
        // JPA 로 수정
//        commonDAO.deleteUsr(user_id);
    }
}
