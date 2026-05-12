package com.parkyc.devog.member.exception;

import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;

public class MemberException extends DevogApiException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
