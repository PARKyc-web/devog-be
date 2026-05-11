package com.parkyc.devog.token.dto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;

public record TokenClaims(
        String loginId,
        List<String> role
) {
}
