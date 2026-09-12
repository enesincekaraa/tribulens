package com.tribulens.match.infrastructure.bootstrap;

import com.tribulens.match.application.port.MatchRepository;
import com.tribulens.match.domain.FootballMatch;
import com.tribulens.match.domain.MatchDataSource;
import com.tribulens.match.domain.MatchStatus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class MatchCatalogBootstrap implements ApplicationRunner {
    private final MatchRepository matchRepository;

    public MatchCatalogBootstrap(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }


    @Override
    public void run(ApplicationArguments args) {

        Instant fetchedAt = Instant.parse("2026-09-12T12:00:00Z");

        matchRepository.save(
                new FootballMatch(
                        UUID.fromString(
                                "c483a561-1af4-4dd4-8398-2ee46f35d101"
                        ),
                        "TRIBULENS-DEMO",
                        "Karadeniz FK",
                        "İstanbul Athletic",
                        Instant.parse("2026-09-15T17:00:00Z"),
                        MatchStatus.SCHEDULED,
                        new MatchDataSource(
                                "TRIBULENS_DEMO",
                                "demo-match-001",
                                fetchedAt
                        )
                )
        );

        matchRepository.save(
                new FootballMatch(
                        UUID.fromString(
                                "ad338349-3b78-49dd-a77e-19c9f95f6712"
                        ),
                        "TRIBULENS-DEMO",
                        "Başkent SK",
                        "Ege United",
                        Instant.parse("2026-09-15T20:00:00Z"),
                        MatchStatus.SCHEDULED,
                        new MatchDataSource(
                                "TRIBULENS_DEMO",
                                "demo-match-002",
                                fetchedAt
                        )
                )
        );


    }
}
