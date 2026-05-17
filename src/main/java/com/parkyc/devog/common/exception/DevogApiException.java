package com.parkyc.devog.common.exception;

import lombok.Getter;

@Getter
public class DevogApiException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public DevogApiException(BaseErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
