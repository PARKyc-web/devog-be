package com.parkyc.devog.common.code;

import lombok.Getter;

@Getter
public enum MemberStatus {
    ACTIVE("00"),
    INACTIVE("01"),
    NOT_VERIFIED("02"),
    LOCK("03");

    private final String code;

    MemberStatus(String code){
        this.code = code;
    }
}
