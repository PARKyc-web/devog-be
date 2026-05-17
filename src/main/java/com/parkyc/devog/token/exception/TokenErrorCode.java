package com.parkyc.devog.token.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements BaseErrorCode {

    // 토큰 파싱 에러
    INVALID_CLAIMS_OBJECT(HttpStatus.BAD_REQUEST, "Claims에 포함될 수 없는 Object가 있습니다."),

    ILLEGAL_ARGUMENT_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰 정보입니다."),

    UNSUPPORTED_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 JWT 토큰 유형입니다."),

    // 토큰 시간 만료
    TOKEN_EXPIRED(HttpStatus.OK, "토큰이 만료되었습니다."),
    TOKEN_BUSINESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 파싱 중 에러가 발생하였습니다.");

    private final HttpStatus status;
    private final String message;

    TokenErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
