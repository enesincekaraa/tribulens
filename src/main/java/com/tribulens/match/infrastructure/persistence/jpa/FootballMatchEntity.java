package com.tribulens.match.infrastructure.persistence.jpa;

import com.tribulens.match.domain.FootballMatch;
import com.tribulens.match.domain.MatchDataSource;
import com.tribulens.match.domain.MatchStatus;
import jakarta.persistence.*;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "football_matches")
public class FootballMatchEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "competition_code", nullable = false, length = 50)
    private String competitionCode;

    @Column(name = "home_team", nullable = false, length = 150)
    private String homeTeam;

    @Column(name = "away_team", nullable = false, length = 150)
    private String awayTeam;

    @Column(name = "kick_off_at", nullable = false)
    private Instant kickOffAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MatchStatus status;

    @Column(name = "source_provider", nullable = false, length = 100)
    private String sourceProvider;

    @Column(name = "source_external_match_id", nullable = false, length = 150)
    private String sourceExternalMatchId;

    @Column(name = "source_fetched_at", nullable = false)
    private Instant sourceFetchedAt;

    protected FootballMatchEntity() {
    }

    private FootballMatchEntity(UUID id, String competitionCode, String homeTeam, String awayTeam, Instant kickOffAt, MatchStatus status, String sourceProvider, String sourceExternalMatchId, Instant sourceFetchedAt) {
        this.id = id;
        this.competitionCode = competitionCode;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.kickOffAt = kickOffAt;
        this.status = status;
        this.sourceProvider = sourceProvider;
        this.sourceExternalMatchId = sourceExternalMatchId;
        this.sourceFetchedAt = sourceFetchedAt;
    }

    static FootballMatchEntity fromDomain(FootballMatch match) {
        Objects.requireNonNull(match, "match must not be null");

        MatchDataSource source=match.getSource();


        return new FootballMatchEntity(
                match.getId(),
                match.getCompetitionCode(),
                match.getHomeTeam(),
                match.getAwayTeam(),
                match.getKickOffAt(),
                match.getStatus(),
                source.provider(),
                source.externalMatchId(),
                source.fetchedAt()
        );


    }


    FootballMatch toDomain() {
        return new FootballMatch(
                id,
                competitionCode,
                homeTeam,
                awayTeam,
                kickOffAt,
                status,
                new MatchDataSource(
                        sourceProvider,
                        sourceExternalMatchId,
                        sourceFetchedAt
                )
        );
    }
}
