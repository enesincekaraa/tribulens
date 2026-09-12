package com.tribulens.match.infrastructure.persistence;

import com.tribulens.match.application.port.MatchRepository;
import com.tribulens.match.domain.FootballMatch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryMatchRepository implements MatchRepository {

    private final ConcurrentHashMap<UUID, FootballMatch> matches = new ConcurrentHashMap<UUID, FootballMatch>();

    @Override
    public void save(FootballMatch match) {

        Objects.requireNonNull(match, "match must not be null");

        FootballMatch existingMatch =
                matches.putIfAbsent(match.getId(), match);

        if (existingMatch != null) {
            throw new IllegalStateException(
                    "Match already exists with id: " + match.getId()
            );
        }
    }

    @Override
    public List<FootballMatch> findAll() {
        return List.copyOf(matches.values());
    }
}
