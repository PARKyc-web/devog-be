package com.parkyc.devog.login.service;

import com.parkyc.devog.common.token.TokenProvider;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginDTO.Response login(LoginDTO.Request loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLoginId(),
                        loginDTO.getPassword()
                )
        );

        TokenProvider.Response token = tokenProvider.renewLoginToken(authentication.getName());

        return LoginDTO.Response.builder()
                .loginId(authentication.getName())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
