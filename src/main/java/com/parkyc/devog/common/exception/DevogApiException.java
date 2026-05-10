package com.parkyc.devog.common.exception;

import com.parkyc.devog.common.code.ResponseCode;
import lombok.Getter;

@Getter
public class DevogApiException extends RuntimeException {

    private final ResponseCode responseCode;
    private final BaseErrorCode exceptionCode;

    public DevogApiException(ResponseCode responseCode){
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.exceptionCode = null;
        // 기존에 사용하던 생성자, 조금씩 수정후 마지막에 삭제 처리
    }

    public DevogApiException(BaseErrorCode errorCode){
        super(errorCode.getMessage());
        this.exceptionCode = errorCode;
        this.responseCode = null; // 기존에 사용하던 내용, 조금씩 변경하기 위해서 일단 NULL 값
    }

}
