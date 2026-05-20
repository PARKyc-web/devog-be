package com.parkyc.devog.member.repository;

import com.parkyc.devog.member.domain.code.OAuthType;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberOAuthRepository extends JpaRepository<MemberOAuth, Long> {

    Optional<MemberOAuth> findByOAuthTypeAndOAuthLoginId(OAuthType type, String oAuthLoginId);
}
