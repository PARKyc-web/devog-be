package com.parkyc.devog.github.service;

import com.parkyc.devog.activity.repository.ActivityRepository;
import com.parkyc.devog.common.exception.BaseErrorCode;
import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.exception.DevogErrorCode;
import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.github.service.dto.ContributeApiResponse;
import com.parkyc.devog.github.service.result.GithubActivityResult;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.member.service.MemberService;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient client;
    private final MemberService memberService;

    public GithubActivityResult loadGithubActivity(ContributesCommand command) {
        MemberOAuth oauth = memberService.getOAuthInfo(command.loginId(), OAuthProvider.GITHUB);
        // Github Graphql 데이터 DTO로 받기
        ContributeApiResponse apiResponse = fetchActivity(command, oauth);

        System.out.println(apiResponse);

        // Parsing & Return Data
        return parseActivity(apiResponse);
    }

    private ContributeApiResponse fetchActivity(ContributesCommand command, MemberOAuth oauth){
        String from = LocalDateTime.of(
                command.yearMonth().atDay(1),
                LocalTime.MIN)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        String to = LocalDateTime.of(
                command.yearMonth().atEndOfMonth(),
                LocalTime.MAX)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", oauth.getOauthUserName());
        paramMap.put("from", from);
        paramMap.put("to", to);

        GithubQuery query = GithubQuery.from(GithubApiProperty.CONTRIBUTE, paramMap);
        ContributeApiResponse response = client.graphql(
                query,
                oauth.getAccessToken(),
                ContributeApiResponse.class
        );

        return response;
    }
    private GithubActivityResult parseActivity(ContributeApiResponse apiResponse){

        return new GithubActivityResult(apiResponse);
    }

}
