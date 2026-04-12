package com.parkyc.devog.member.repository;

import com.parkyc.devog.member.domain.entity.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Long> {
}
