package com.tribulens.match.infrastructure.persistence.jpa;

import com.tribulens.match.application.port.MatchRepository;
import com.tribulens.match.domain.FootballMatch;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
public class JpaMatchRepository implements MatchRepository {
    private final FootballMatchJpaRepository jpaRepository;

    public JpaMatchRepository(FootballMatchJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public void save(FootballMatch match) {
        Objects.requireNonNull(match, "match must not be null");
        FootballMatchEntity entity = FootballMatchEntity.fromDomain(match);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FootballMatch> findAll() {
        return jpaRepository.findAllByOrderByKickOffAtAsc()
                .stream()
                .map(FootballMatchEntity::toDomain)
                .toList();
    }
}
