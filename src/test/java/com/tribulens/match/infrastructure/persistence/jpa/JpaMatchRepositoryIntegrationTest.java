package com.tribulens.match.infrastructure.persistence.jpa;

import com.tribulens.TestcontainersConfiguration;
import com.tribulens.match.application.port.MatchRepository;
import com.tribulens.match.domain.FootballMatch;
import com.tribulens.match.domain.MatchDataSource;
import com.tribulens.match.domain.MatchStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional
class JpaMatchRepositoryIntegrationTest {

    private final MatchRepository matchRepository;

    @Autowired
    JpaMatchRepositoryIntegrationTest(
            MatchRepository matchRepository
    ) {
        this.matchRepository = matchRepository;
    }

    @Test
    void shouldPersistAndRestoreCompleteMatch() {
        UUID matchId = UUID.fromString(
                "00000000-0000-0000-0000-000000000100"
        );

        FootballMatch expectedMatch = new FootballMatch(
                matchId,
                "TRIBULENS-TEST",
                "Anadolu FK",
                "Marmara SK",
                Instant.parse("2026-10-01T18:00:00Z"),
                MatchStatus.SCHEDULED,
                new MatchDataSource(
                        "TRIBULENS_TEST",
                        "integration-match-100",
                        Instant.parse("2026-09-12T12:00:00Z")
                )
        );

        matchRepository.save(expectedMatch);

        FootballMatch actualMatch = matchRepository
                .findAll()
                .stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst()
                .orElseThrow();

        assertAll(
                () -> assertEquals(
                        expectedMatch.getId(),
                        actualMatch.getId()
                ),
                () -> assertEquals(
                        expectedMatch.getCompetitionCode(),
                        actualMatch.getCompetitionCode()
                ),
                () -> assertEquals(
                        expectedMatch.getHomeTeam(),
                        actualMatch.getHomeTeam()
                ),
                () -> assertEquals(
                        expectedMatch.getAwayTeam(),
                        actualMatch.getAwayTeam()
                ),
                () -> assertEquals(
                        expectedMatch.getKickOffAt(),
                        actualMatch.getKickOffAt()
                ),
                () -> assertEquals(
                        expectedMatch.getStatus(),
                        actualMatch.getStatus()
                ),
                () -> assertEquals(
                        expectedMatch.getSource(),
                        actualMatch.getSource()
                )
        );
    }
}