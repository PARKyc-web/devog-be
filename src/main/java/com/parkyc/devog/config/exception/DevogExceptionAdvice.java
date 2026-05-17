package com.parkyc.devog.config.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DevogExceptionAdvice {

    @ExceptionHandler(DevogApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleDevogException(DevogApiException e){
        BaseErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOtherException(Exception e){

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<Void>(
                        false,
                        "INTERNAL_SERVER_ERROR",
                        "예상치 못한 에러가 발생했습니다. 담당자에게 문의해주세요!",
                        null)
                );
    }

}
