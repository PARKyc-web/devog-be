package com.parkyc.devog.common.response;

import com.parkyc.devog.common.exception.BaseErrorCode;

public record ApiResponse<T>(
        boolean result,
        String message,
        T data
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "SUCCESS", data);
    }

    public static ApiResponse<Void> fail(BaseErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getMessage(), null);
    }
}
