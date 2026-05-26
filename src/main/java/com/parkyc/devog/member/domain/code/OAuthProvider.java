package com.parkyc.devog.member.domain.code;

public enum OAuthProvider {
    GITHUB,
    NOTION,
    GOOGLE;

    public static OAuthProvider from(String provider){
        try {
            return OAuthProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("provider를 제대로 입력하세요");
        }
    }

}
