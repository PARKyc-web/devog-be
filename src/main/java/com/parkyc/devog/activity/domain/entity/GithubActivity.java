package com.parkyc.devog.activity.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table
public class GithubActivity {

    @Id
    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "sha")
    private String sha;

    @Column(name = "repository")
    private String repository;

    @Column(name = "repository_owner")
    private String repositoryOwner;

    @Column(name = "commit_message")
    private String commitMessage;

    @Column(name = "activity_at")
    private LocalDateTime activityAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
