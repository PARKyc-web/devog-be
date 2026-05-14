package com.parkyc.devog.login.service.result;

public record LoginResult(
    String loginId,
    String accessToken,
    String refreshToken
) {}
