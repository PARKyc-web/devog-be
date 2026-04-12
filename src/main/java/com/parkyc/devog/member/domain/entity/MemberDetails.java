package com.parkyc.devog.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "KEY_GITHUB")
    private String keyGithub;

    @Column(name = "KEY_NOTION")
    private String keyNotion;
}
