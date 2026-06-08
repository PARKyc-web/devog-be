package com.parkyc.devog.activity.service;

import com.parkyc.devog.activity.domain.entity.Activity;
import com.parkyc.devog.activity.repository.ActivityRepository;
import com.parkyc.devog.activity.service.command.ActivityCommand;
import com.parkyc.devog.activity.service.result.ActivityDayCount;
import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final GithubClient githubClient;

    // Think : 깃허브 상세내역 조회 기능 필요
    // Think : load -> find -> fetch
    // Think : 저장해둔 활동내역 조회
    public ActivityDayCount findActivity(ActivityCommand command){
        List<Activity> activities = activityRepository.findAllBymemberIdAndActionTimeBetween(
                command.memberId(),
                command.fromDate(),
                command.toDate()
        );

        return null;
    }



}
