package com.parkyc.devog.login.service;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    LoginDTO.Response login(LoginDTO.Request loginDTO);

}
