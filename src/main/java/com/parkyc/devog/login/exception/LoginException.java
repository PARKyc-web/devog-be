package com.parkyc.devog.login.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;
import lombok.Getter;

@Getter
public class LoginException extends DevogApiException {

    public LoginException(LoginErrorCode errorCode) {
        super(errorCode);
    }
}
