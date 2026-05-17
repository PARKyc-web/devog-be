package com.parkyc.devog.login.service;

import com.parkyc.devog.login.service.command.LoginCommand;
import com.parkyc.devog.login.service.result.LoginResult;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.security.DevogUserDetails;
import com.parkyc.devog.token.dto.LoginClaim;
import com.parkyc.devog.token.dto.LoginToken;
import com.parkyc.devog.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public LoginResult login(LoginCommand command){
        // Spring Security를 사용해서 로그인 검증
        // 실패 시, new LoginException(LoginErrorCode.INVALID_LOGIN_ID)
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.loginId(),
                        command.password()
                )
        );
        DevogUserDetails detail = (DevogUserDetails) auth.getPrincipal();

        LoginToken token = tokenService.issueLoginToken(
                new LoginClaim(detail.getLoginId(), null)
        );

        return new LoginResult(detail.getLoginId(), token);
    }


}
