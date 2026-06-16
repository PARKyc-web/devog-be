package com.parkyc.devog.github.service;

import com.parkyc.devog.github.GithubApiProperty;
import com.parkyc.devog.github.GithubClient;
import com.parkyc.devog.github.GithubQuery;
import com.parkyc.devog.github.service.command.ContributesCommand;
import com.parkyc.devog.github.service.dto.CommitApiResponse;
import com.parkyc.devog.github.service.dto.ContributeApiResponse;
import com.parkyc.devog.github.service.dto.ParsedContribute;
import com.parkyc.devog.github.service.result.GithubActivityResult;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient client;
    private final MemberService memberService;

    /** Github Activity Load (Fetch & Parsing) */
    public List<GithubActivityResult> loadGithubActivity(ContributesCommand command) {
        /**
         * 4가지 단계를 돌면서 Github Activity 데이터를 파싱한다.
         * 1. Fetch Github Contributes
         * 2. Parse Contributes (ContributeApiResponse -> List<ParsedContribute>)
         * 3. Fetch Github Commit Info Using List<ParsedContribute>
         * 4. Parse Commit Info ( List<CommitApiResponse> -> List<GithubActivityResult>)
         * */

        MemberOAuth oauth = memberService.getOAuthInfo(command.loginId(), OAuthProvider.GITHUB);
        // Github Graphql 데이터 DTO로 받기
        ContributeApiResponse apiResponse = fetchContributes(command, oauth);
        List<ParsedContribute> commitSummary = parseContributes(apiResponse);

        List<CommitApiResponse> commitResponse = fetchCommitList(commitSummary, command, oauth);

        return parseCommitList(commitResponse);
    }

    /**
     * Raw response:
     * {
     *   "data": {
     *     "user": {
     *       "contributionsCollection": {
     *         "commitContributionsByRepository": [
     *           {
     *             "repository": {
     *               "name": "repository",
     *               "owner": { "login": "owner" },
     *               "url": "https://github.com/owner/repository"
     *             },
     *             "contributions": {
     *               "nodes": [
     *                 {
     *                   "occurredAt": "2026-06-01T00:00:00Z",
     *                   "commitCount": 1
     *                 }
     *               ]
     *             }
     *           }
     *         ]
     *       }
     *     }
     *   }
     * }
     */
    private ContributeApiResponse fetchContributes(ContributesCommand command, MemberOAuth oauth){
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

    private List<ParsedContribute> parseContributes(ContributeApiResponse apiResponse){
        HashMap<String, ParsedContribute> contributeMap = new HashMap<>();

        for (ContributeApiResponse.CommitContributionsByRepository contribution
                : apiResponse.data().user().contributionsCollection().commitContributionsByRepository()) {
            String repository = contribution.repository().name();
            String owner = contribution.repository().owner().login();
            String key = owner + "/" + repository;

            for (ContributeApiResponse.Node node : contribution.contributions().nodes()) {
                ParsedContribute parsedContribute = contributeMap.get(key);
                int commitCount = node.commitCount();

                if (parsedContribute != null) {
                    commitCount += parsedContribute.commitCount();
                }

                contributeMap.put(key, new ParsedContribute(repository, owner, commitCount));
            }
        }

        return new ArrayList<>(contributeMap.values());
    }

    /**
     * Raw response:
     * [
     *   {
     *     "sha": "commit-sha",
     *     "html_url": "https://github.com/owner/repository/commit/commit-sha",
     *     "commit": {
     *       "message": "commit message",
     *       "author": {
     *         "name": "author",
     *         "email": "author@example.com",
     *         "date": "2026-06-01T12:00:00Z"
     *       },
     *       "committer": {
     *         "name": "committer",
     *         "email": "committer@example.com",
     *         "date": "2026-06-01T12:00:00Z"
     *       }
     *     },
     *     "author": {
     *       "login": "github-login"
     *     },
     *     "parents": [
     *       { "sha": "parent-sha" }
     *     ]
     *   }
     * ]
     */
    private List<CommitApiResponse> fetchCommitList(List<ParsedContribute> parsedContributes,
                                                    ContributesCommand command,
                                                    MemberOAuth oauth){
        String since = LocalDateTime.of(
                command.yearMonth().atDay(1), LocalTime.MIN)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        String until = LocalDateTime.of(
                command.yearMonth().atEndOfMonth(), LocalTime.MAX)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        List<CommitApiResponse> result = new ArrayList<>();

        for(ParsedContribute data : parsedContributes){
            String url = String.format(
                    "/repos/%s/%s/commits?author=%s&since=%s&until=%s&per_page=100",
                    data.owner(), data.repository(), oauth.getOauthUserName(), since, until
            );
            tools.jackson.databind.JsonNode apiResponse = client.get(
                    url,
                    oauth.getAccessToken(),
                    tools.jackson.databind.JsonNode.class
            );

            for (tools.jackson.databind.JsonNode commitNode : apiResponse) {
                tools.jackson.databind.JsonNode commit = commitNode.get("commit");
                tools.jackson.databind.JsonNode author = commit.get("author");
                String sha = commitNode.get("sha").asText();

                result.add(new CommitApiResponse(
                        sha,
                        commit.get("message").asText(),
                        author.get("name").asText(),
                        OffsetDateTime.parse(author.get("date").asText()).toLocalDateTime(),
                        data.repository(),
                        data.owner()
                ));
            }
        }

        return result;
    }

    private List<GithubActivityResult> parseCommitList(List<CommitApiResponse> commitApiResponses){
        List<GithubActivityResult> results = new ArrayList<>();

        for (CommitApiResponse commitApiResponse : commitApiResponses) {
            results.add(new GithubActivityResult(
                    commitApiResponse.sha(),
                    commitApiResponse.message(),
                    commitApiResponse.repository(),
                    commitApiResponse.owner(),
                    commitApiResponse.date()
            ));
        }

        return results;
    }
}


/**
 * 1. Contribute 정보를 가져온다 -> commit이 발생한 repo를 확인한다.
 * 2. GET /repos/{owner}/{repo}/commits 를 사용해서, commit의 sha를 알아낸다.
 *
 *  3. GET /repos/{owner}/{repo}/commits/{sha}를 사용해서, Activity에 저장할 내용을 알아낸다.
 *   (sha, add, remove, repository, commit-message, commit-date)
 *
 * 2번까지만 저장하고, sha 및 commitCount를 저장하고 있다가 상세화면에 들어가면 sha로 조회해서 뿌려준다?
 * 그럼 달력에서 클릭했을때는 commitMessage 및 repository 이름 정도만 보여준다.
 *
 * */
