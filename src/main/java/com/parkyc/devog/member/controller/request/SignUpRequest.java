package com.parkyc.devog.member.controller.request;

import com.parkyc.devog.member.service.command.SignUpCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
    @NotNull @NotBlank String loginId,
    @NotNull @NotBlank String password,
    String nickname
) {
    public SignUpCommand toCommand(){
        String nickname = this.nickname;
        if(this.nickname == null || this.nickname.isBlank()){
            // setRandNickName();
            nickname = "rand-nickname";
        }

        return new SignUpCommand(loginId, password, nickname);
    }
}
