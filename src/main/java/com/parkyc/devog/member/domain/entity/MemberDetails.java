package com.parkyc.devog.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name= "MEMBER_DETAILS")
public class MemberDetails {

    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NICKNAME")
    private String nickname;

    @MapsId
    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
