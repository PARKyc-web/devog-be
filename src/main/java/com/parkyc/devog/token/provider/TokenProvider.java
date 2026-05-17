package com.parkyc.devog.token.provider;

import com.parkyc.devog.token.dto.LoginClaim;
import com.parkyc.devog.token.dto.TokenClaim;
import com.parkyc.devog.token.dto.TokenResult;
import com.parkyc.devog.token.code.TokenType;
import com.parkyc.devog.token.exception.TokenErrorCode;
import com.parkyc.devog.token.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TokenProvider {

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
    public TokenClaim verifyToken(String token){

        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (MalformedJwtException e) {
            log.info(e.getMessage(), e);
            throw new TokenException(TokenErrorCode.ILLEGAL_ARGUMENT_TOKEN);
        } catch (SignatureException e) {
            log.info(e.getMessage(), e);
            throw new TokenException(TokenErrorCode.ILLEGAL_ARGUMENT_TOKEN);
        } catch(UnsupportedJwtException e) {
            log.info(e.getMessage(), e);
            throw new TokenException(TokenErrorCode.UNSUPPORTED_TOKEN_TYPE);
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage(), e);
            throw new TokenException(TokenErrorCode.TOKEN_EXPIRED);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new TokenException(TokenErrorCode.TOKEN_BUSINESS_ERROR);
        }

        return toRecord(claims);
    }

    /**
     * Access/Refresh Token 발급
     * @param data
     * @param type
     * @return TokenResult
     */
    public TokenResult issueToken(TokenClaim data, TokenType type){
        Date now = new Date();
        Date expire = new Date(now.getTime() + (type == TokenType.ACCESS ? accessExpire : refreshExpire));

        String token = Jwts.builder()
                .signWith(secretKey)
                .issuer("DEVOG Application")
                .issuedAt(now)
                .expiration(expire)
                .claims(toClaims(data))
                .compact();

        return new TokenResult(token, expire.toInstant());
    }

    /**
     * TokenClaims(Record)를 Claims로 컨버팅 (Record to Claims)
     * @param data
     * @return Claims
     */
    private Claims toClaims(TokenClaim data){
        return Jwts.claims()
                .add(data.map())
                .build();
    }

    /**
     * Claims를 TokenClaim(Record)로 컨버팅 (Claims to Record)
     * @param claims
     * @return TokenClaim
     */
    private TokenClaim toRecord(Claims claims){
        Map<String, Object> map = new HashMap<>();
        for(String key : claims.keySet()){
            map.put(key, claims.get(key));
        }

        return new TokenClaim(map);
    }

}
