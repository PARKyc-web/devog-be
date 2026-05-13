package com.parkyc.devog.member.service.command;

public record SignUpCommand(
    String loginId,
    String password,
    String nickname
) {}
