package com.parkyc.devog.github.service;

import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import com.parkyc.devog.github.exception.GithubErrorCode;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient client;
    private final MemberRepository memberRepository;

    public String fetchContributes(ContributesCommand command){

        Optional<Member> oMember = memberRepository.findByLoginId(command.loginId());
        if(oMember.isEmpty()){
            // Think : 에러코드 다듬기
            throw new DevogApiException(GithubErrorCode.GITHUB_ERROR_EXCEPTION);
        }

        Member member = oMember.get();
        MemberOAuth oauth = member.getOauths()
                .stream()
                .filter(o -> o.getOauthProvider() == OAuthProvider.GITHUB)
                .findFirst()
                .orElseThrow((() -> new DevogApiException(GithubErrorCode.GITHUB_ERROR_EXCEPTION)));


        HashMap<String, Object> param = new HashMap<>();
        param.put("login", oauth.getOauthUserName());

        // Think : 나중에 LocalDateUtil 같은거 만들어서,
        // LocalDate -> LocalDateTime, LocalDateTime -> Seoul LocalDateTime, String -> LocalDateTime 으로 만들지?
        param.put("from", command.fromDate()
                .atStartOfDay()
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));

        param.put("to", command.toDate()
                .atTime(23, 59, 59)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));

        GithubQuery query = GithubQuery.from(GithubApiProperty.CONTRIBUTE, param);

        JsonNode result = client.graphql(query, oauth.getAccessToken());

        log.info("GITHUB CONTRIBUTE RESULT !!! ");
        log.info(result.toString());

        return result.toString();
    }

}
