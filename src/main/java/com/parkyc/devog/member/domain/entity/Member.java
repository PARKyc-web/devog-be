package com.parkyc.devog.member.domain.entity;

import com.parkyc.devog.common.code.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name="MEMBER",
        indexes = @Index(name = "idx_login_id", columnList = "LOGIN_ID")
)
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @SequenceGenerator(name = "MEMBER_ID_GENERATOR", sequenceName = "SEQ_MEMBER_ID"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_ID_GENERATOR")
    private Long memberId;

    @Column(name = "LOGIN_ID", unique = true)
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

/* 연동 여부는 MEMBER_OAUTH 테이블의 row 존재 여부로 체크 */
//    @Column(name = "AUTH_GITHUB")
//    private boolean authGithub;
//
//    @Column(name = "AUTH_NOTION")
//    private boolean authNotion;
//
//    @Column(name = "AUTH_GOOGLE")
//    private boolean authGoogle;

    @OneToOne(mappedBy = "member")
    private MemberDetails details;

    @OneToMany(mappedBy = "member")
    private List<MemberOAuth> oauths;
}

