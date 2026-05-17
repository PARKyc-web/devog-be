package com.parkyc.devog.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getStatus();
    String getMessage();

    default String getCode(){
        if (this instanceof Enum<?> enumValue) {
            return enumValue.name();
        }

        throw new DevogApiException(DevogErrorCode.ERROR_CODE_OVERRIDE);
    }
}
