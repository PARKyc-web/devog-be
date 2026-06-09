package com.parkyc.devog.github.service.result;

import com.parkyc.devog.github.service.dto.ContributeApiResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GithubActivityResult (
    String sha,
    String message,
    String repository,
    String owner,
    LocalDateTime commitDate

    /** 또 뭐 필요하지?
     * GET /repos/{owner}/{repo}/commits/{sha}
     * 위의 URL로 상세정보를 확인할꺼니까....
     *
     *
     * contribute
     * → owner/repo/date 범위 확보
     *
     * repo별 commits API
     * → sha/message/date/url 확보
     *
     * commit detail API
     * → 상세 클릭 시 files/patch 확보
     *
     * GET /repos/{owner}/{repo}/commits
     * sha
     * commit.message
     * commit.author.date
     * html_url
     * author
     * committer
     * parents
     * */
) {
}
