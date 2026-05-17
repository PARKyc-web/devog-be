package com.parkyc.devog.login.controller;

import com.parkyc.devog.common.response.ApiResponse;
import com.parkyc.devog.login.controller.request.LoginRequest;
import com.parkyc.devog.login.controller.response.LoginResponse;
import com.parkyc.devog.login.controller.response.LoginResponseWriter;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.login.service.result.LoginResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LoginResponseWriter writer;

    // 자체 회원가입 계정으로 로그인한다.
    @PostMapping("")
    public ApiResponse<LoginResponse>  login(@Valid @RequestBody LoginRequest login,
                                             HttpServletRequest request,
                                             HttpServletResponse response){
        LoginResult result = loginService.login(login.toCommand());

        return ApiResponse.ok(
                writer.write(request, response, result)
        );
        /*
        return ApiResponse.ok(
                new LoginResponse(result.loginId(),
                result.accessToken(),
                result.refreshToken())
        );
        */
    }

    @PostMapping("/github")
    public LoginResponse githubLogin(){

        return null;
    }
    @PostMapping("/notion")
    public LoginResponse notionLogin(){
        return null;
    }

}
