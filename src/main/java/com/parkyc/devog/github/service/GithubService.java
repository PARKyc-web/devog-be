package com.parkyc.devog.github.service;

import com.parkyc.devog.member.repository.MemberOAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final MemberOAuthRepository oAuthRepository;

    public String loadContributes(String loginId){

        // login-Id 기반 AT, RT 조회

        //

        return "";
    }

}
