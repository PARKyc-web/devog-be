package com.parkyc.devog.token.service;

import com.parkyc.devog.token.dto.LoginClaim;
import com.parkyc.devog.token.dto.LoginToken;
import com.parkyc.devog.token.dto.TokenClaim;
import com.parkyc.devog.token.dto.TokenResult;
import com.parkyc.devog.token.code.TokenType;
import com.parkyc.devog.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider provider;

    /**
     * Login 시, Access/Refresh 토큰 전부 발급
     * @param claims
     * @return LoginToken
     */
    public LoginToken issueLoginToken(LoginClaim claims){

        Map<String, Object> map = new HashMap<>();
        map.put("loginId", claims.loginId());
        map.put("role", claims.role());

        // Access Token 발급
        TokenResult aToken = provider.issueToken(
                new TokenClaim(map),
                TokenType.ACCESS
        );
        // Refresh Token 발급
        TokenResult rToken = provider.issueToken(
                new TokenClaim(map),
                TokenType.REFRESH
        );

        return new LoginToken(aToken, rToken);
    }

    public TokenResult renewAccessToken(String refreshToken){
        

        return null;
    }

}
