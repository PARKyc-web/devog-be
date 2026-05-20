package com.parkyc.devog.security;

import java.util.List;

public record DevogPrincipal(
    String loginId,
    List<String> roles
){}