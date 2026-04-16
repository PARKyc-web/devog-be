package com.parkyc.devog.security;

import com.parkyc.devog.common.code.MemberStatus;
import com.parkyc.devog.member.domain.entity.Member;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class DevogUserDetails implements UserDetails {

    private final String loginId;
    private final String password;
    private final String nickname;
    private final MemberStatus status;
    private final Collection<? extends GrantedAuthority> authorities;

    public DevogUserDetails(Member member){
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.nickname = member.getDetails() == null ? null : member.getDetails().getNickname();
        this.status = member.getStatus();
        this.authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    public DevogUserDetails(){
        this.loginId = "TEST-USER";
        this.password = "TEST-PASSWORD";
        this.nickname = "TEST-USER";
        this.status = MemberStatus.ACTIVE;
        this.authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
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
