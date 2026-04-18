package com.parkyc.devog.config.http;

import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.config.exception.DevogApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(DevogApiException.class)
    public CommonDTO.Response<Void> handleDevogException(DevogApiException e){
        return CommonDTO.fail(e.getResponseCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonDTO.Response<Void> handleException(Exception e){
        return CommonDTO.fail(ResponseCode.API_ERROR);
    }
}
