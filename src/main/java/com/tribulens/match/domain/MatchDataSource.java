package com.tribulens.match.domain;

import java.time.Instant;
import java.util.Objects;

public record MatchDataSource(
        String provider,
        String externalMatchId,
        Instant fetchedAt
) {
    public MatchDataSource{
        provider=requireText(provider,"provider");
        externalMatchId=requireText(externalMatchId,"externalMatchId");
        Objects.requireNonNull(fetchedAt, "fetchedAt must not be null");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );        }
        return value.trim();
    }
}
