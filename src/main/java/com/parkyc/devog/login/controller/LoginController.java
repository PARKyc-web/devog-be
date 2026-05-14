package com.parkyc.devog.login.controller;

import com.parkyc.devog.login.controller.request.LoginRequest;
import com.parkyc.devog.login.controller.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lgn")
@RequiredArgsConstructor
public class LoginController {

    // 자체 회원가입 계정으로 로그인한다.
    @PostMapping("/self")
    public LoginResponse login(@Valid LoginRequest request){



        return new LoginResponse();
    }

}
