package com.parkyc.devog.common.response;

import com.parkyc.devog.common.exception.BaseErrorCode;

public record ApiResponse<T>(
        boolean result,
        String code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "SUCCESS", "SUCCESS", data);
    }

    public static ApiResponse<Void> fail(BaseErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }
}
