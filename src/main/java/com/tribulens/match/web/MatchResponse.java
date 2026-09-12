package com.tribulens.match.web;

import com.tribulens.match.domain.FootballMatch;
import com.tribulens.match.domain.MatchStatus;

import java.time.Instant;
import java.util.UUID;

public record MatchResponse(
        UUID id,
        String competitionCode,
        String homeTeam,
        String awayTeam,
        Instant kickOffAt,
        MatchStatus status,
        SourceResponse source
) {

    public static MatchResponse from(FootballMatch match) {
        return new MatchResponse(
                match.getId(),
                match.getCompetitionCode(),
                match.getHomeTeam(),
                match.getAwayTeam(),
                match.getKickOffAt(),
                match.getStatus(),
                new SourceResponse(
                        match.getSource().provider(),
                        match.getSource().externalMatchId(),
                        match.getSource().fetchedAt()
                )
        );
    }


    private record SourceResponse(
            String provider,
            String externalMatchId,
            Instant fetchedAt
    ){}
}
