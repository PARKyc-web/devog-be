package com.parkyc.devog.member.controller;

import com.parkyc.devog.member.controller.request.SignUpRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    public void signUp(@Valid SignUpRequest request){

    }
}
