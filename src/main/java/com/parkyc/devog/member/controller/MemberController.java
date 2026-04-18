package com.parkyc.devog.member.controller;

import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.member.domain.dto.MemberDTO;
import com.parkyc.devog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원가입
     * @param signUp
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody MemberDTO.SignUp signUp){

        // 로그인 -> 추가정보 입력
        return new CommonDTO.Response<String>(ResponseCode.API_SUCCESS, "TEST String Object").toEntity();
    }

    /**
     * 회원 추가정보 입력
     * @return
     */
    @PostMapping("/details")
    public String enrollDetails(@RequestBody MemberDTO.Details details){

        return "";
    }
}
