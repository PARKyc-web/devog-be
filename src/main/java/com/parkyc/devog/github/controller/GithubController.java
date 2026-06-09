package com.parkyc.devog.github.controller;

import com.parkyc.devog.github.service.GithubService;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.github.service.result.GithubActivityResult;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    // Think : Github, notion, google 등 패키지 구조 생각하기
    // Think : GithubController 는 확인용으로 만든것으로, Service만 있으면 될 것 같음 -> 다른 기능에서 사용
    @GetMapping("/contribute")
    public List<GithubActivityResult> getContribute(@AuthenticationPrincipal DevogPrincipal user){
        ContributesCommand command = new ContributesCommand(user.loginId(), YearMonth.of(2026, 5));

        return githubService.loadGithubActivity(command);
    }
}


