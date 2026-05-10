package com.parkyc.devog.member.controller;

import com.parkyc.devog.common.CookieProvider;
import com.parkyc.devog.common.HeaderProvider;
import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.login.service.LoginService;
import com.parkyc.devog.member.domain.dto.MemberDTO;
import com.parkyc.devog.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final HeaderProvider headerProvider;

    private final CookieProvider cookieProvider;

    /**
     * 회원가입
     * @param signUp
     * @return
     */
    @PostMapping("/sign-up")
    public CommonDTO.Response<LoginDTO.Response> signup(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        @RequestBody MemberDTO.SignUp signUp){

        CommonDTO.Response<MemberDTO.SignUp> res = memberService.signUp(signUp);
        MemberDTO.SignUp signUpMember = res.getData();

        LoginDTO.Request loginDTO = LoginDTO.Request.builder()
                .loginId(signUpMember.getLoginId())
                .password(signUpMember.getPassword())
                .build();
        LoginDTO.Response loginRes = loginService.login(loginDTO);

        if(headerProvider.isAppPlatform(request)){
            return new CommonDTO.Response<>(ResponseCode.SUCCESS_SIGN_UP, loginRes);
        }

        // Web에서 회원가입한 경우, 쿠키에 refresh-token을 세팅해준다.
        ResponseCookie cookie = cookieProvider.getRefreshCookie(loginRes);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        loginRes.setRefreshToken(null);
        return new CommonDTO.Response<>(ResponseCode.SUCCESS_SIGN_UP, loginRes);
    }

    /**
     * 회원 추가정보 입력
     * @return
     */
    @PostMapping("/details")
    public String enrollDetails(@RequestBody MemberDTO.Details details){

        return "";
    }

    public void linkGithubData(){
    }
}
