package com.parkyc.devog.github.service.dto;

public record ParsedContribute(
        String repository,
        String owner,
        int commitCount
) {
}
