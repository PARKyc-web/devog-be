package com.parkyc.devog.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DevogErrorCode implements BaseErrorCode{

    BUSINESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 에러가 발생했습니다. 관리자에게 문의해주세요"),
    ERROR_CODE_OVERRIDE(HttpStatus.INTERNAL_SERVER_ERROR, "BaseErrorCode의 기본 getCode를 사용하기 위해서는 Enum에 상속하여야 합니다.")
    ;

    private final HttpStatus status;
    private final String message;

    DevogErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
