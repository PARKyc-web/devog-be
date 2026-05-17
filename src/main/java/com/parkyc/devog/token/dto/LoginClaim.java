package com.parkyc.devog.token.dto;

import java.util.List;

public record LoginClaim(
        String loginId,
        List<String> role
) {
}
