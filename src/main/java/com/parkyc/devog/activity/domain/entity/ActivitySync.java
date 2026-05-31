package com.parkyc.devog.activity.domain.entity;

import com.parkyc.devog.member.domain.code.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table
public class ActivitySync {

    @Id
    @Column(name = "sync_id")
    private Long syncId;

    @Column(name = "provider")
    private OAuthProvider provider;

    private LocalDateTime lastedSyncDate;

    @Column(name = "sync_at")
    private LocalDateTime syncAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
