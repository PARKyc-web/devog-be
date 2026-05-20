package com.parkyc.devog.login.controller.response;

public record LoginResponse(
    boolean isOAuth,
    boolean isFirst,
    String loginId,
    String accessToken,
    String refreshToken
) {}

/**
 * isOAuth : OAuth Login 여부
 * isFirst : 최초 로그인 여부
 * loginId : 현재 로그인 아이디
 * accessToken : 엑세스 토큰
 * refreshToken : 리프레쉬 토큰
 */