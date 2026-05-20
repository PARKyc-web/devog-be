package com.parkyc.devog.member.domain.entity;


import com.parkyc.devog.member.domain.code.OAuthType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member_oauth"
)
public class MemberOAuth {

    @Id
    @Column(name = "oauth_id")
    @SequenceGenerator(
            name = "generator_oauth_id", sequenceName = "seq_oauth",
            allocationSize = 1, initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_oauth_id")
    private Long oAuthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "oauth_type")
    @Enumerated(EnumType.STRING)
    private OAuthType oAuthType;

    @Column(name="oauth_login_id")
    private String oAuthLoginId;

    @Column(name = "oauth_access_tokne")
    private String accessToken;

    @Column(name = "oauth_refresh_token")
    private String refreshToken;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 정적 팩토리 메소드
    public static MemberOAuth of(OAuthType type, String accessToken, String refreshToken){
        MemberOAuth oAuth = new MemberOAuth();
        oAuth.oAuthType = type;
        oAuth.accessToken = accessToken;
        oAuth.refreshToken = refreshToken;

        return oAuth;
    }

    public void assignMember(Member member){
        this.member = member;
    }
}
