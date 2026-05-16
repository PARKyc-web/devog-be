package com.parkyc.devog.login.service;

import com.parkyc.devog.login.exception.LoginErrorCode;
import com.parkyc.devog.login.exception.LoginException;
import com.parkyc.devog.login.service.command.LoginCommand;
import com.parkyc.devog.login.service.result.LoginResult;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.member.service.MemberService;
import com.parkyc.devog.security.DevogUserDetails;
import com.parkyc.devog.token.dto.TokenClaims;
import com.parkyc.devog.token.dto.TokenType;
import com.parkyc.devog.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authManager;
    private final TokenProvider tokenProvider;

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

        // token 발급
        String aToken = tokenProvider.issueToken(
                new TokenClaims(detail.getLoginId(), null),
                TokenType.ACCESS
        );
        String rToken = tokenProvider.issueToken(
                new TokenClaims(detail.getLoginId(), null),
                TokenType.REFRESH
        );

        return new LoginResult(detail.getLoginId(), aToken, rToken);
    }


}
