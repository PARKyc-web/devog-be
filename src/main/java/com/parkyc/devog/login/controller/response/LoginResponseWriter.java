package com.parkyc.devog.login.controller.response;

import com.parkyc.devog.login.service.result.LoginResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginResponseWriter {

    private final String HEADER_DEVOG_AGENT = "devog-agent";
    private final String WEB_AGENT = "web-agent";
    private final String APP_AGENT = "app-agent";

    public LoginResponse write(HttpServletRequest request,
                                      HttpServletResponse response,
                                      LoginResult result){

        String agent = request.getHeader(HEADER_DEVOG_AGENT);
        if(agent == null || agent.isBlank() || WEB_AGENT.equals(agent)){
            Cookie cookie = new Cookie("refresh-token", result.token().refreshToken().value());
            cookie.setHttpOnly(true);
            // localhost 에서 사용하기 위해서 막아둠
            // cookie.setSecure(true);
            cookie.setSecure(false);
            cookie.setPath("/");

            response.addCookie(cookie);

            return new LoginResponse(false,
                    false,
                    result.loginId(),
                    result.token().accessToken().value(),
                    null
            );
        }

        return new LoginResponse(false,
                false,
                result.loginId(),
                result.token().accessToken().value(),
                result.token().refreshToken().value()
        );
    }
}
