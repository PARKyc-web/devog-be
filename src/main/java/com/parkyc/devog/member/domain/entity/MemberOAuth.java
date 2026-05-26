package com.parkyc.devog.member.domain.entity;


import com.parkyc.devog.member.domain.code.OAuthProvider;
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

    @SequenceGenerator(
            name = "generator_oauth_id", sequenceName = "seq_oauth",
            allocationSize = 1, initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_oauth_id")
    @Id
    @Column(name = "oauth_id")
    private Long oauthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;

    @Column(name="oauth_login_id")
    private String oauthUserId;

    @Column(name="oauth_user_name")
    private String oauthUserName;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 정적 팩토리 메소드
    public static MemberOAuth of(Member member,
                                 OAuthProvider provider,
                                 String accessToken, String refreshToken,
                                 String oauthUserId, String oauthUserName){
        MemberOAuth oauth = new MemberOAuth();
        oauth.member = member;
        oauth.oauthProvider = provider;
        oauth.accessToken = accessToken;
        oauth.refreshToken = refreshToken;
        oauth.oauthUserId = oauthUserId;
        oauth.oauthUserName = oauthUserName;

        return oauth;
    }

    public void assignMember(Member member){
        this.member = member;
    }
}
