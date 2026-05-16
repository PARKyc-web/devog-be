package com.parkyc.devog.security;

import com.parkyc.devog.login.exception.LoginErrorCode;
import com.parkyc.devog.login.exception.LoginException;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.exception.MemberErrorCode;
import com.parkyc.devog.member.exception.MemberException;
import com.parkyc.devog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DevogUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new LoginException(LoginErrorCode.INVALID_LOGIN_ID));

        return new DevogUserDetails(member);
    }
}
