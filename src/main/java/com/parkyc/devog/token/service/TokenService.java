package com.parkyc.devog.token.service;

import com.parkyc.devog.token.dto.TokenInfo;
import com.parkyc.devog.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider provider;

    public TokenInfo renewAccessToken(String refreshToken){
        


        return new TokenInfo(true, "", "");
    }

}
