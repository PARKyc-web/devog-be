package com.parkyc.devog.token.exception;

import com.parkyc.devog.common.exception.DevogApiException;

public class TokenException extends DevogApiException {

    public TokenException(TokenErrorCode code) {
        super(code);
    }
}
