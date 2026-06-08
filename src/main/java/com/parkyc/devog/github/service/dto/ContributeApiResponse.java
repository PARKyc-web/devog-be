package com.parkyc.devog.github.service.dto;

import java.util.List;

public record ContributeApiResponse(
        Data data
) {

    public record Data(
            User user
    ) {
    }

    public record User(
            ContributionsCollection contributionsCollection
    ) {
    }

    public record ContributionsCollection(
            List<CommitContributionsByRepository> commitContributionsByRepository
    ) {
    }

    public record CommitContributionsByRepository(
            Repository repository,
            Contributions contributions
    ) {
    }

    public record Repository(
            String name,
            Owner owner,
            String url
    ) {
    }

    public record Owner(
            String login
    ) {
    }

    public record Contributions(
            List<Node> nodes
    ) {
    }

    public record Node(
            String occurredAt,
            Integer commitCount
    ) {
    }
}
