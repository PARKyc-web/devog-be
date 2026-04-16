package com.parkyc.devog.security;

import com.parkyc.devog.member.domain.entity.Member;
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

        Optional<Member> auth = memberRepository.findByLoginId(username);
        if(auth.isEmpty()){
            throw new UsernameNotFoundException("CAN NOT FIND USER");
        }

        return new DevogUserDetails(auth.get());
    }
}
