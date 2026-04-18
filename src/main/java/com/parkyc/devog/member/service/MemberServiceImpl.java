package com.parkyc.devog.member.service;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.member.domain.dto.MemberDTO;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.repository.MemberDetailsRepository;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberDetailsRepository detailsRepository;
    private final MemberOAuthRepository oAuthRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginDTO.Response signUp(MemberDTO.SignUp signUp) {

        boolean isDup = memberRepository.existsByLoginId(signUp.getLoginId());
        if(isDup){
            return null;
        }





        return null;
    }
}
