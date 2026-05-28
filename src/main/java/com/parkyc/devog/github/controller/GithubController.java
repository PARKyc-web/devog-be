package com.parkyc.devog.github.controller;

import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubClient client;
    private final MemberRepository memberRepository;
    private final MemberOAuthRepository memberOAuthRepository;

    // Think : Github, notion, google 등 패키지 구조 생각하기
    // Think : GithubController 는 확인용으로 만든것으로, Service만 있으면 될 것 같음 -> 다른 기능에서 사용
    @GetMapping("/contribute")
    public String getContribute(@AuthenticationPrincipal DevogPrincipal user){

        String loginId = user.loginId();
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        Member m = member.get();
        List<MemberOAuth> oauth = m.getOauths();

        System.out.println(oauth);

//
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("login", "PARKyc-web");
//        param.put("from", "2026-05-01T00:00:00Z");
//        param.put("to", "2026-05-20T23:59:59Z");
//
//        GithubQuery query = GithubQuery.from(GithubApiProperty.CONTRIBUTE, param);
//
//        JsonNode result = client.graphql(query, "key-value");
//
//        System.out.println(result);
//        return result.toString();
        return "test";
    }
}


