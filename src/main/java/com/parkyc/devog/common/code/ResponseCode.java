package com.parkyc.devog.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    /* Basic Response Code */
    API_SUCCESS(HttpStatus.OK, "Success API"),
    API_FAIL(HttpStatus.OK, "Fail API"),
    API_WRONG_INPUT(HttpStatus.OK,"Fail API - Wrong Input"),
    API_ERROR(HttpStatus.OK,"Fail API - Error Occur"),

    /* Login Response Code */
    WRONG_INPUT(HttpStatus.OK, "Please Input Correct ID or Password"),
    LOGIN_INVALID_CREDENTIALS(HttpStatus.OK, "INVALID ID or Password"),

    /* Token Response Code */
    TOKEN_EXPIRED(HttpStatus.OK, "Token Expired");

    /* Http Status Response Code */

    private final HttpStatus status;
    private final String message;

    ResponseCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
