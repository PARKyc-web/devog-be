package com.parkyc.devog.login.service.command;

public record LoginCommand(
    String loginId,
    String password
) {}
