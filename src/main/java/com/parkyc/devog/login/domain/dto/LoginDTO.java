package com.parkyc.devog.login.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
