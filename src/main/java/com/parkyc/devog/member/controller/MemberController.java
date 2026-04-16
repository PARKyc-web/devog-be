package com.parkyc.devog.member.controller;

import com.parkyc.devog.member.domain.dto.MemberDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @PostMapping("/sign-up")
    public String signup(@RequestBody MemberDTO.SignUp signUp){

        return "";
    }
}
