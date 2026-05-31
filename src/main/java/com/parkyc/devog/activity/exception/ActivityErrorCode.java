package com.parkyc.devog.activity.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum ActivityErrorCode implements BaseErrorCode {

    ACTIVITY_ERROR_CODE(HttpStatus.OK, "message");

    private final HttpStatus status;
    private final String message;

    ActivityErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
