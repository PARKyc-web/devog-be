package com.parkyc.devog.token.service;

import com.parkyc.devog.token.dto.TokenClaims;
import com.parkyc.devog.token.dto.TokenInfo;
import com.parkyc.devog.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider provider;

    /**
     * 회원 정보를 받아 로그인 토큰(Access + Refresh) 생성
     * @return
     */
    public TokenInfo issueLoginToken(){

        // 1. 회원 정보 조회 ( 아이디, 회원 권한 )
        // 2. Claims로 만들어서 토큰 생성 요청
        new TokenClaims("", new ArrayList<>());

        // 3. 토큰 생성한거 리턴

        return TokenInfo.builder().build();
    }

    /**
     * RefreshToken을 사용해 AccessToken 재발급(갱신)
     * @param refreshToken
     * @return TokenInfo
     */
    public TokenInfo renewAccessToken(String refreshToken){

        // Refresh-Token 검증
        //


        return TokenInfo.builder().build();
    }
}
