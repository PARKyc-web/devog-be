package com.parkyc.devog.token.dto;

import java.time.Instant;

public record TokenResult(
        String value,
        Instant expire
) {
}

/** 가장 기본이 되는 토큰의 리턴 값의 기본 형태 */
