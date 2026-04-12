package com.parkyc.devog.login.service;

import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.security.DevogUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public UserDetails login(LoginDTO.Request loginDTO) {

        // UserDetails을 반환하면 안되고, Access/Refresh Token을 반환해줘야 함.
        // DTO 하나 만들어서 반환하자.



        return new DevogUserDetails();
    }
}
