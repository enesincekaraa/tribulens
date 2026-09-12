package com.tribulens.match.domain;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public final class FootballMatch {
    private final UUID id;
    private final String competitionCode;
    private final String homeTeam;
    private final String awayTeam;
    private final Instant kickOffAt;
    private final MatchStatus status;
    private final MatchDataSource source;


    public FootballMatch(
            UUID id,
            String competitionCode,
            String homeTeam,
            String awayTeam,
            Instant kickOffAt,
            MatchStatus status,
            MatchDataSource source
    ) {
        this.id = Objects.requireNonNull(
                id,
                "id must not be null"
        );

        this.competitionCode = requireText(
                competitionCode,
                "competitionCode"
        ).toUpperCase(Locale.ROOT);

        this.homeTeam = requireText(
                homeTeam,
                "homeTeam"
        );

        this.awayTeam = requireText(
                awayTeam,
                "awayTeam"
        );

        if (this.homeTeam.equalsIgnoreCase(this.awayTeam)) {
            throw new IllegalArgumentException(
                    "homeTeam and awayTeam must be different"
            );
        }

        this.kickOffAt = Objects.requireNonNull(
                kickOffAt,
                "kickOffAt must not be null"
        );

        this.status = Objects.requireNonNull(
                status,
                "status must not be null"
        );

        this.source = Objects.requireNonNull(
                source,
                "source must not be null"
        );
    }

    public UUID getId() {
        return id;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Instant getKickOffAt() {
        return kickOffAt;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public MatchDataSource getSource() {
        return source;
    }

    private static String requireText(
            String value,
            String fieldName
    ){
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }

        return value.trim();
    }
}
