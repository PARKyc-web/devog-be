package com.parkyc.devog.member.domain.code;

public enum OAuthType {
    GITHUB,
    NOTION,
    GOOGLE;

    public static OAuthType from(String provider){
        try {
            return OAuthType.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("provider를 제대로 입력하세요");
        }
    }
}
