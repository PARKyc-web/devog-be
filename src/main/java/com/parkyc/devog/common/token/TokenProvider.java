package com.parkyc.devog.common.token;

import com.parkyc.devog.security.DevogUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    /**
     *  Token 결과 반환 클래스
     */
    @Data
    @Builder
    public static class Response {
        private boolean result;
        private String accessToken;
        private String refreshToken;
    }

    private final SecretKey secretKey;
    private final long accessExpire;
    private final long refreshExpire;

    public TokenProvider(@Value("${token.key}") String key,
                         @Value("${token.expire.access}") long accessExpire,
                         @Value("${token.expire.refresh}") long refreshExpire){

        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
    }

    /**
     * Token 검증 및 Claims 반환
     * @param token
     * @return
     */
    public Claims verifyToken(String token){

        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            log.error("Token is Expired", e);
            return null;
        } catch (Exception e){
            log.error("Token is Something Wrong", e);
            return null;
        }

        return claims;
    }

    /**
     * RefreshToken을 사용해, AccessToken 재발급
     * @param refreshToken
     * @return
     */
    public Response renewAccessToken(String refreshToken){

        Claims claims = verifyToken(refreshToken);
        if(claims == null){
            return Response.builder()
                    .result(false)
                    .build();
        }

        // loginid, isuuer, isuueAt(new Date()), status

        return Response.builder().build();
    }

    /**
     * 로그인 시, Access/Refresh Token 발급
     * @return
     */
    public Response renewLoginToken(String loginId){

        Date now = new Date();
        Claims claims = Jwts.claims()
                .issuer("DEVOG")
                .issuedAt(now)
                .add("id", loginId)
                .build();

        String access = Jwts.builder()
                .claims(claims)
                .expiration(new Date(now.getTime() + accessExpire))
                .compact();
        String refresh = Jwts.builder()
                .claims(claims)
                .expiration(new Date(now.getTime() + refreshExpire))
                .compact();

        return Response.builder()
                .result(true)
                .accessToken(access)
                .refreshToken(refresh)
                .build();
    }

}
