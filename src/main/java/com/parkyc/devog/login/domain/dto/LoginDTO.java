package com.parkyc.devog.login.domain.dto;

import lombok.Builder;
import lombok.Data;

public class LoginDTO {

    @Data
    @Builder
    public static class Request {
        private String loginId;
        private String password;
    }

    @Data
    @Builder
    public static class Response {
        private String loginId;
        private String accessToken;
        private String refreshToken;
    }
}
