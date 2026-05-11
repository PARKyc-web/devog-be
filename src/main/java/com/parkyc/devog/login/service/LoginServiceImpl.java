package com.parkyc.devog.login.service;

import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.token.dto.TokenInfo;
import com.parkyc.devog.token.provider.TokenProvider;
import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

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

        // TokenInfo token = tokenProvider.renewLoginToken(authentication.getName());
        TokenInfo token = tokenService.issueLoginToken();

        return LoginDTO.Response.builder()
                .loginId(authentication.getName())
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
