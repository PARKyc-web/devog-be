package com.parkyc.devog.token.dto;

import lombok.Builder;

@Builder
public record TokenInfo(
        boolean result,
        String accessToken,
        String refreshToken
) {
}

/**
 * TokenInfo의 경우, 토큰 정보를 파싱한 결과 데이터이기 때문에 추후에 변경이 되면 안 됨.
 * 하여 record로 불변성을 보장함.
 */