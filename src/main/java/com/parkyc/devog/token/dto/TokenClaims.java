package com.parkyc.devog.token.dto;

import java.util.List;

public record TokenClaims(
        String loginId,
        List<String> role
) {
}
