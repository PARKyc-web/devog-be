package com.parkyc.devog.login.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;

public class LoginException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public LoginException(LoginErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
