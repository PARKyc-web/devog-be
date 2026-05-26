package com.parkyc.devog.integration.exception;

import com.parkyc.devog.common.exception.DevogApiException;

public class IntegrationException extends DevogApiException {

    public IntegrationException(IntegrationErrorCode errorCode) {
        super(errorCode);
    }
}
