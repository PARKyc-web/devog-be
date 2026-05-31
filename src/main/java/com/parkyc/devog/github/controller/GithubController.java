package com.parkyc.devog.github.controller;

import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import com.parkyc.devog.github.service.GithubService;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    // Think : Github, notion, google 등 패키지 구조 생각하기
    // Think : GithubController 는 확인용으로 만든것으로, Service만 있으면 될 것 같음 -> 다른 기능에서 사용
    @GetMapping("/contribute")
    public String getContribute(@AuthenticationPrincipal DevogPrincipal user){

        ContributesCommand command = new ContributesCommand(
                user.loginId(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );

        githubService.fetchContributes(command);

        return "test";
    }
}


