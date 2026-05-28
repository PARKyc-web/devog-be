package com.parkyc.devog.activity.repository;

import com.parkyc.devog.activity.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByMemberIdAndActionTime(Long memberId, LocalDateTime actionTime);

    List<Activity> findAllBymemberIdAndActionTimeBetween(Long memberId, LocalDate actionTimeAfter, LocalDate actionTimeBefore);
}
