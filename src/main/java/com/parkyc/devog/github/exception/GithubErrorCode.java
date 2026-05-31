package com.parkyc.devog.github.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum GithubErrorCode implements BaseErrorCode {

    GITHUB_ERROR_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "messge");

    private final HttpStatus status;
    private final String message;

    GithubErrorCode(HttpStatus status, String message){
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
