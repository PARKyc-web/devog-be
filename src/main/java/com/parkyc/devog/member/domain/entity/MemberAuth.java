package com.parkyc.devog.member.domain.entity;

import com.parkyc.devog.common.code.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name="MEMBER_AUTH",
        indexes = @Index(name = "idx_login_id", columnList = "LOGIN_ID")
)
public class MemberAuth {

    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(name = "AUTH_GITHUB")
    private boolean authGithub;

    @Column(name = "AUTH_NOTION")
    private boolean authNotion;
}


