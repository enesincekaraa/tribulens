package com.tribulens.match.application.port;

import com.tribulens.match.domain.FootballMatch;

import java.util.List;

public interface MatchRepository {
    void save(FootballMatch match);
    List<FootballMatch> findAll();
}
