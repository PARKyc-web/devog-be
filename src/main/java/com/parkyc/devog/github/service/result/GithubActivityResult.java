package com.parkyc.devog.github.service.result;

import java.time.LocalDateTime;

public record GithubActivityResult (
    String sha,
    String message,
    String repository,
    String owner,
    LocalDateTime commitDate
) {
}
