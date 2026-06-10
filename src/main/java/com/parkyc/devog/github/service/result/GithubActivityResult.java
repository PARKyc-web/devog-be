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
) {

    public record CommitFiles(
        String name,
        int add,
        int remove
    ){ }
}
