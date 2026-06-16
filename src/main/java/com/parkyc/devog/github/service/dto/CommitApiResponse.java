package com.parkyc.devog.github.service.dto;

import java.time.LocalDateTime;

public record CommitApiResponse(
        String sha,
        String message,
        String author,
        LocalDateTime date,
        String repository,
        String owner
) {
}
