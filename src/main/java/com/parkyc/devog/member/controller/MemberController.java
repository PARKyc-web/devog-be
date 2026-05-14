package com.parkyc.devog.member.controller;

import com.parkyc.devog.member.controller.request.SignUpRequest;
import com.parkyc.devog.member.controller.response.SignUpResponse;
import com.parkyc.devog.member.service.MemberService;
import com.parkyc.devog.member.service.result.SignUpResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@Valid SignUpRequest request){
        SignUpResult result = memberService.signUp(request.toCommand());

        return new SignUpResponse(true, "SUCCESS", result.loginId());
    }

}
