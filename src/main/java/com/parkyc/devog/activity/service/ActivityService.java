package com.parkyc.devog.activity.service;

import com.parkyc.devog.activity.service.command.ActivityCommand;
import com.parkyc.devog.activity.service.result.ActivityDayCount;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.service.GithubService;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.github.service.result.GithubActivityResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final GithubService githubService;

    public List<GithubActivityResult> test(){

        ContributesCommand command = new ContributesCommand(
                "test001",
                YearMonth.of(2026, 6)
        );

        return githubService.loadGithubActivity(command);
    }


}
