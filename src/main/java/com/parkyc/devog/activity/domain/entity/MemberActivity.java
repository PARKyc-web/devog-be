package com.parkyc.devog.activity.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEMBER_ACTIVITY")
public class MemberActivity {

    @Id
    @Column(name = "ACTIVITY_ID")
    private long activityId;
}
