package com.parkyc.devog.member.controller.response;

public record SignUpResponse(
    boolean result,
    String message,
    String loginId
) {}
