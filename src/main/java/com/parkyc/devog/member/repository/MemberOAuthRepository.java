package com.parkyc.devog.member.repository;

import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberOAuthRepository extends JpaRepository<MemberOAuth, Long> {

    Optional<MemberOAuth> findByOauthProviderAndOauthUserId(OAuthProvider provider, String oauthUserId);

    List<MemberOAuth> findByMember(Member member);
}
