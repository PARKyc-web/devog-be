package com.parkyc.devog.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@Slf4j
@Component
public class GithubClient {

    private final RestClient client;

    public GithubClient() {
        this.client = RestClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    public JsonNode graphql(GithubQuery query, String key) {
        return client.post()
                .uri("/graphql")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                .body(query)
                .retrieve()
                .body(JsonNode.class);
    }

}
