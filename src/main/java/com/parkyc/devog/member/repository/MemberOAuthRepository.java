package com.parkyc.devog.member.repository;

import com.parkyc.devog.member.domain.entity.MemberOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOAuthRepository extends JpaRepository<MemberOAuth, Long> {
}
