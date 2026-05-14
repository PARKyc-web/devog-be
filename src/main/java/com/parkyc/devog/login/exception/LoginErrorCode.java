package com.parkyc.devog.login.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum LoginErrorCode implements BaseErrorCode {

    NO_EXISTS_MEMBER(HttpStatus.BAD_REQUEST, "회원가입되지 않은 계정입니다.");

    private final HttpStatus status;
    private final String message;

    LoginErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
