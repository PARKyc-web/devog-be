package com.parkyc.devog.config;

public final class WHITE_LIST {

    // Spring Security 및 Filter에서 사용하기 위해서 public static final로 설정
    public static final String[] URLS = {
            "/login/**",
            "/member/sign-up",
            "/integration/**"
    };

    private WHITE_LIST(){}
}
