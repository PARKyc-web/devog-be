package com.parkyc.devog.github;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GithubQuery {
    private String query;
    private Map<String, Object> variables;

    public static GithubQuery from(GithubApiProperty query, Map<String, Object> variables){
        GithubQuery githubQuery = new GithubQuery();

        githubQuery.query = query.getQuery();
        githubQuery.variables = variables;

        return githubQuery;
    }
}
