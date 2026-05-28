package com.parkyc.devog.token.dto;

import java.util.List;

public record LoginClaim(
        Long memberId,
        String loginId,
        List<String> roles
) {
}
