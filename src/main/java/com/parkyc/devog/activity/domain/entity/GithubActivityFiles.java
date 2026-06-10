package com.parkyc.devog.activity.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "github_activity_files")
public class GithubActivityFiles {

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private GithubActivity activity;

    @Column(name = "file_name")
    private String fileName;

    // Think : 나중에 enum으로 변경하는거 고민해볼 것
    @Column(name = "status")
    private String status;

    @Column(name = "additions")
    private int additions;

    @Column(name = "deletions")
    private int deletions;

    @Column(name = "file_url")
    private String fileUrl;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
