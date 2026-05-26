package com.parkyc.devog.github;

import lombok.Getter;

@Getter
public enum GithubApiProperty {

    CONTRIBUTE("""
            query($login: String!, $from: DateTime!, $to: DateTime!) { user(login: $login) { contributionsCollection(from: $from, to: $to) { contributionCalendar { totalContributions weeks { contributionDays { date contributionCount color } } } } } }
            """);

    private final String query;

    GithubApiProperty(String query){
        this.query = query;
    }
}
