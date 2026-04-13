package com.parkyc.devog.member.repository;

import com.parkyc.devog.common.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberOAuthRepository extends JpaRepository<MemberOAuth, Long> {

    Optional<MemberOAuth> findByProviderAndProviderUserId(OAuthProvider provider, String providerUserId);

    Optional<MemberOAuth> findByMemberMemberIdAndProvider(Long memberId, OAuthProvider provider);
}
