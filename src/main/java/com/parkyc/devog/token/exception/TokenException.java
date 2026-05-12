package com.parkyc.devog.token.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;

public class TokenException extends DevogApiException {

    public TokenException(TokenErrorCode errorCode) {
        super(errorCode);
    }
}
