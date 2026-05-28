package com.parkyc.devog.activity.service;

import com.parkyc.devog.activity.domain.entity.Activity;
import com.parkyc.devog.activity.repository.ActivityRepository;
import com.parkyc.devog.activity.service.command.ActivityCommand;
import com.parkyc.devog.activity.service.result.ActivityDayCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityDayCount findActivity(ActivityCommand command){
        List<Activity> activities = activityRepository.findAllBymemberIdAndActionTimeBetween(
                command.memberId(),
                command.fromDate(),
                command.toDate()
        );

        return null;
    }
}
