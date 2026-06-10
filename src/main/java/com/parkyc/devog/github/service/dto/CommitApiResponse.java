package com.parkyc.devog.github.service.dto;

import java.util.List;

public record CommitApiResponse(
        String sha
) {

    public record CommitFile(
            String name,
            int add,
            int remove
    ){}
}
