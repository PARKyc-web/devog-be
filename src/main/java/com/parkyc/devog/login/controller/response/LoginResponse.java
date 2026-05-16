package com.parkyc.devog.login.controller.response;

public record LoginResponse(
    String loginId,
    String accessToken,
    String refreshToken
) {}
