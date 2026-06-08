package com.parkyc.devog.activity.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "activity",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_activity_member_source_external",
                columnNames = {"member_id", "source", "external_id"}
        )
)
public class Activity {

    @Id
    @Column(name = "activity_id")
    @SequenceGenerator(name = "ACTIVITY_ID_GENERATOR", sequenceName = "SEQ_ACTIVITY_ID",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITY_ID_GENERATOR")
    private Long activityId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // Think : 나중에 Enum을 하나 만들어서 표시할 지 생각할 것.
    @Column(name = "source")
    private String source;

    // Think : 나중에 Enum을 하나 만들어서 표시할 지 생각할 것. action 자체는 API에서 내려주는 값 그대로 사용하는게 어떤지?
    @Column(name = "action")
    private String action; // 무슨 활동인지? commit?, 글쓰기? 등등

    @Column(name = "action_time")
    private LocalDate actionTime;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "repository_owner")
    private String repositoryOwner;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Activity githubCommit(Long memberId,
                                        String repositoryOwner,
                                        String repositoryName,
                                        String commitSha,
                                        String commitUrl,
                                        String title,
                                        LocalDate actionTime) {
        Activity activity = new Activity();

        activity.memberId = memberId;
        activity.source = "GITHUB";
        activity.action = "COMMIT";
        activity.externalId = commitSha;
        activity.externalUrl = commitUrl;
        activity.repositoryOwner = repositoryOwner;
        activity.repositoryName = repositoryName;
        activity.title = title;
        activity.actionTime = actionTime;

        return activity;
    }
}
