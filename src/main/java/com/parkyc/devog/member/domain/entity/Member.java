package com.parkyc.devog.member.domain.entity;

import com.parkyc.devog.member.domain.code.MemberStatus;
import com.parkyc.devog.member.exception.MemberErrorCode;
import com.parkyc.devog.member.exception.MemberException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "MEMBER",
        indexes = @Index(name = "idx_member_created_at", columnList = "created_at DESC")
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

    @Column(name="nickname")
    private String nickname;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberOAuth> oauths = new ArrayList<>();


    public static Member signup(
            String loginId,
            String password,
            String nickname
    ){
        if(loginId == null || loginId.isBlank()){
            throw new MemberException(MemberErrorCode.ERROR);
        }
        if(password == null || password.isBlank()){
            throw new MemberException(MemberErrorCode.ERROR);
        }

        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.status = MemberStatus.ACTIVE;
        member.nickname = nickname;

        return member;
    }

    public void connectOauth(MemberOAuth oAuth){
        this.oauths.add(oAuth);
        oAuth.assignMember(this);
    }
}

