package com.parkyc.devog.login.service;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    UserDetails login(LoginDTO.Request loginDTO);

}
