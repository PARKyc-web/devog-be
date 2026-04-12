package com.parkyc.devog.security;

import com.parkyc.devog.member.domain.entity.MemberAuth;
import com.parkyc.devog.member.repository.MemberAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DevogUserDetailService implements UserDetailsService {

    private final MemberAuthRepository memberAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MemberAuth> auth = memberAuthRepository.findByLoginId(username);
        if(auth.isEmpty()){
            throw new RuntimeException("CAN NOT FIND USER");
        }

        return new DevogUserDetails();
    }
}
