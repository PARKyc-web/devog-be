package com.parkyc.devog.member.domain.code;

import lombok.Getter;

@Getter
public enum MemberStatus {
    ACTIVE,
    INACTIVE,
    NOT_VERIFIED,
    LOCK

    // 일단 이메일 인증은 뺄까? 일단 기능부터 만들고 추가하자.
}
