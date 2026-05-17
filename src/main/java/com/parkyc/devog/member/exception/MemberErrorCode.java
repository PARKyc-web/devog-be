package com.parkyc.devog.member.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements BaseErrorCode {

    ALREADY_USED_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 ID 입니다."),
    ERROR(HttpStatus.OK, "SUCCESS TO API");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
