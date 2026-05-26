package com.parkyc.devog.github.controller;

import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

import java.util.HashMap;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubClient client;

    // Think : Github, notion, google 등 패키지 구조 생각하기
    @GetMapping("/contribute")
    public String getContribute(){

        HashMap<String, Object> param = new HashMap<>();
        param.put("login", "PARKyc-web");
        param.put("from", "2026-05-01T00:00:00Z");
        param.put("to", "2026-05-20T23:59:59Z");

        GithubQuery query = GithubQuery.from(GithubApiProperty.CONTRIBUTE, param);

        JsonNode result = client.graphql(query, "key-value");

        System.out.println(result);
        return result.toString();
    }
}


