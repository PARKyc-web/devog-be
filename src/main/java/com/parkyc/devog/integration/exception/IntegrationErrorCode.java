package com.parkyc.devog.integration.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum IntegrationErrorCode implements BaseErrorCode {

    INTEGRATION_ERROR(HttpStatus.BAD_REQUEST, "1234")
    ;

    private final HttpStatus status;
    private final String message;

    IntegrationErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
