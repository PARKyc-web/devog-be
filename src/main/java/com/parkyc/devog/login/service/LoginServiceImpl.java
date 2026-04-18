package com.parkyc.devog.login.service;

import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.token.TokenProvider;
import com.parkyc.devog.config.exception.DevogApiException;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginDTO.Response login(LoginDTO.Request loginDTO) {

        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getLoginId(),
                            loginDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new DevogApiException(ResponseCode.LOGIN_INVALID_CREDENTIALS);
        } catch (DisabledException e) {
            throw new DevogApiException(ResponseCode.API_ERROR);
        } catch (LockedException e) {
            throw new DevogApiException(ResponseCode.API_ERROR);
        } catch (AuthenticationException e) {
            throw new DevogApiException(ResponseCode.API_ERROR);
        }

        TokenProvider.Response token = tokenProvider.renewLoginToken(authentication.getName());

        return LoginDTO.Response.builder()
                .loginId(authentication.getName())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
