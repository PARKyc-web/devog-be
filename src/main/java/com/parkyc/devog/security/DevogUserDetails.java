package com.parkyc.devog.security;

import com.parkyc.devog.member.domain.code.MemberStatus;
import com.parkyc.devog.member.domain.entity.Member;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class DevogUserDetails implements UserDetails {

    private final Long memberId;
    private final String loginId;
    private final String password;
    private final String nickname;
    private final MemberStatus status;
    private final Collection<? extends GrantedAuthority> authorities;

    public DevogUserDetails(){
        this.memberId = 0L;
        this.loginId = "TEST-USER";
        this.password = "TEST-PASSWORD";
        this.nickname = "TEST-USER";
        this.status = MemberStatus.ACTIVE;
        this.authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    public DevogUserDetails(Member member){
        this.memberId = member.getMemberId();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.status = member.getStatus();
        this.authorities = Collections.emptyList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }
}
