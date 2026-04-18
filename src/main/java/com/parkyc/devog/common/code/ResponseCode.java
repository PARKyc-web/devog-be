package com.parkyc.devog.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    /* Basic Response Code */
    API_SUCCESS(HttpStatus.OK, "Success API"),
    API_FAIL(HttpStatus.OK, "Fail API"),
    API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Fail API - Error Occur"),

    /* Member Response Code */
    DUPLICATE_LOGIN_ID(HttpStatus.OK, "Login Id is Already Used"),
    SUCCESS_SIGN_UP(HttpStatus.OK, "Success to sign-up, Redirect Details Inputs"),

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
