package com.parkyc.devog.login.controller.request;

import com.parkyc.devog.login.service.command.LoginCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull @NotBlank String loginId,
    @NotNull @NotBlank String password
) {

    public LoginCommand toCommand(){
        return new LoginCommand(loginId, password);
    }
}
