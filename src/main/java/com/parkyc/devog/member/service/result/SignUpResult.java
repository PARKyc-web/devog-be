package com.parkyc.devog.member.service.result;

import com.parkyc.devog.member.domain.MemberStatus;

import java.time.LocalDateTime;

public record SignUpResult(
    Long memberId,
    String loginId,
    String password,
    String nickname,
    MemberStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
