package com.parkyc.devog.activity.domain.entity;

import com.parkyc.devog.member.domain.code.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table
public class ActivitySync {

    @Id
    @Column(name = "sync_id", comment = "pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITY_SYNC_GENERATOR")
    @SequenceGenerator(name = "SEQ_SYNC_ID", sequenceName = "ACTIVITY_SYNC_GENERATOR",
            allocationSize = 1, initialValue = 1)
    private Long syncId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, comment = "")
    private OAuthProvider provider;

    @Column(name = "activity_year", nullable = false)
    private Integer activityYear;

    @Column(name = "activity_month", nullable = false)
    private Integer activityMonth;

    @Column(name = "synced_to_date", comment = "활동내역을 연계한 날짜")
    private LocalDate syncedToDate;

    @Column(name = "sync_at", comment = "마지막 데이터 동기화 시간")
    private LocalDateTime syncAt;

    @Column(name = "sync_finished", comment = "현재 달의 데이터 동기화가 완료되었는지 여부")
    private boolean syncFinished;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
