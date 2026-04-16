package com.parkyc.devog.member.domain.dto;

import lombok.Builder;
import lombok.Data;

public class MemberDTO {

    @Data
    @Builder
    public static class SignUp {
        private String loginId;
        private String password;
    }

}
