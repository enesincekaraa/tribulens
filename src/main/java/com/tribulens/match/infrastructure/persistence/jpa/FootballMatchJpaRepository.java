package com.tribulens.match.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FootballMatchJpaRepository extends JpaRepository<FootballMatchEntity, UUID> {

    List<FootballMatchEntity> findAllByOrderByKickOffAtAsc();
}
