package com.parkyc.devog.security;

import java.util.List;

public record DevogPrincipal(
        Long memberId,
        String loginId,
        List<String> roles
){}