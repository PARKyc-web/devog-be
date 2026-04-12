package com.parkyc.devog.member.repository;

import com.parkyc.devog.member.domain.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {

    Optional<MemberAuth> findByLoginId(String loginId);
}
