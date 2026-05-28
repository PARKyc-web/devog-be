package com.parkyc.devog.github;

import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.exception.DevogErrorCode;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
public enum GithubApiProperty {

    CONTRIBUTE("""
            query($login: String!, $from: DateTime!, $to: DateTime!) { user(login: $login) { contributionsCollection(from: $from, to: $to) { contributionCalendar { totalContributions weeks { contributionDays { date contributionCount color } } } } } }
            """,
            Set.of("login", "from", "to"));

    private final String query;
    private final Set<String> required;

    GithubApiProperty(String query, Set<String> required){
        this.query = query;
        this.required = required;
    }

    public void validVariables(Map<String, Object> param){
        for(String key : required){
            boolean result = param.containsKey(key);
            if(!result){
                throw new DevogApiException(DevogErrorCode.BUSINESS_ERROR);
            }
        }
    }
}
