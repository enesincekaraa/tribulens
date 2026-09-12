package com.tribulens.match;

import com.tribulens.match.application.ListMatchesService;
import com.tribulens.match.domain.FootballMatch;
import com.tribulens.match.domain.MatchDataSource;
import com.tribulens.match.domain.MatchStatus;
import com.tribulens.match.infrastructure.persistence.InMemoryMatchRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchCatalogBehaviorTest {

    @Test
    void shouldRejectMatchBetweenSameTeam() {

        IllegalArgumentException exception=assertThrows(
                IllegalArgumentException.class,
                ()-> createMatch(
                        UUID.fromString(
                                "00000000-0000-0000-0000-000000000001"
                        ),
                        "Trabzonspor",
                        " trabzonspor ",
                        "2026-09-15T17:00:00Z"
                )
        );
        assertEquals(
                "homeTeam and awayTeam must be different",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectDuplicateMatchId() {
        InMemoryMatchRepository repository =new InMemoryMatchRepository();


        FootballMatch match = createMatch(
                UUID.fromString(
                        "00000000-0000-0000-0000-000000000002"
                ),
                "Karadeniz FK",
                "İstanbul Athletic",
                "2026-09-15T17:00:00Z"
        );

        repository.save(match);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> repository.save(match)
        );

        assertEquals(
                "Match already exists with id: " + match.getId(),
                exception.getMessage()
        );

    }

    @Test
    void shouldListMatchesByKickOffTime() {
        InMemoryMatchRepository repository =new InMemoryMatchRepository();

        FootballMatch laterMatch = createMatch(
                UUID.fromString(
                        "00000000-0000-0000-0000-000000000003"
                ),
                "Başkent SK",
                "Ege United",
                "2026-09-15T20:00:00Z"
        );

        FootballMatch earlierMatch = createMatch(
                UUID.fromString(
                        "00000000-0000-0000-0000-000000000004"
                ),
                "Karadeniz FK",
                "İstanbul Athletic",
                "2026-09-15T17:00:00Z"
        );

        repository.save(laterMatch);
        repository.save(earlierMatch);

        ListMatchesService service = new ListMatchesService(repository);

        List<FootballMatch> result = service.execute();


        assertEquals(
                List.of(earlierMatch.getId(),laterMatch.getId()),
                result.stream()
                        .map(FootballMatch::getId)
                        .toList()
        );

    }

        private FootballMatch createMatch(
            UUID id,
            String homeTeam,
            String awayTeam,
            String kickOffAt
    ){
        return new FootballMatch(
                id,
                "TRIBULENS-DEMO",
                homeTeam,
                awayTeam,
                Instant.parse(kickOffAt),
                MatchStatus.SCHEDULED,
                new MatchDataSource(
                        "TRIBULENS_TEST",
                        "external-" + id,
                        Instant.parse("2026-09-12T12:00:00Z")
                )
        );
    }
}
