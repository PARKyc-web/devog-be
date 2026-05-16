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
