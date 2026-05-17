package com.parkyc.devog.login.service.result;

import com.parkyc.devog.token.dto.LoginToken;

public record LoginResult(
    String loginId,
    LoginToken token
) {}
