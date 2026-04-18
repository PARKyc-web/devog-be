package com.parkyc.devog.config.exception;

import com.parkyc.devog.common.code.ResponseCode;
import lombok.Getter;

@Getter
public class DevogApiException extends RuntimeException {

    private final ResponseCode responseCode;

    public DevogApiException(ResponseCode responseCode){
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
