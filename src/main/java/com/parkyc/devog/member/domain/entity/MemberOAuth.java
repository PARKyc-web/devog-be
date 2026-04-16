package com.parkyc.devog.member.domain.entity;

import com.parkyc.devog.common.code.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "MEMBER_OAUTH",
        indexes = {
                @Index(name = "idx_oauth_member_id", columnList = "MEMBER_ID"),
                @Index(name = "idx_oauth_provider_user_id", columnList = "PROVIDER, PROVIDER_USER_ID")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_oauth_provider_user", columnNames = {"PROVIDER", "PROVIDER_USER_ID"}),
                @UniqueConstraint(name = "uk_oauth_member_provider", columnNames = {"MEMBER_ID", "PROVIDER"})
        }
)
public class MemberOAuth {

    @Id
    @Column(name = "OAUTH_ID")
    @SequenceGenerator(name = "OAUTH_ID_GENERATOR", sequenceName = "SEQ_MEMBER_OAUTH_ID"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OAUTH_ID_GENERATOR")
    private Long oauthId;

    @Column(name = "PROVIDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(name = "PROVIDER_USER_ID", nullable = false)
    private String providerUserId;

    @Column(name = "PROVIDER_LOGIN")
    private String providerLogin;

    @Column(name = "PROVIDER_EMAIL")
    private String providerEmail;

    @Column(name = "ACCESS_TOKEN", length = 2048)
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", length = 2048)
    private String refreshToken;

    @Column(name = "TOKEN_TYPE")
    private String tokenType;

    @Column(name = "SCOPE", length = 1024)
    private String scope;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @Column(name = "CONNECTED_AT")
    private LocalDateTime connectedAt;

    @Column(name = "REVOKED_AT")
    private LocalDateTime revokedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
}
