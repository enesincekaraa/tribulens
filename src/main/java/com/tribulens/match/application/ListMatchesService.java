package com.tribulens.match.application;

import com.tribulens.match.application.port.MatchRepository;
import com.tribulens.match.domain.FootballMatch;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public final class ListMatchesService {

    private final MatchRepository matchRepository;
    public ListMatchesService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<FootballMatch> execute() {
        return matchRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(FootballMatch::getKickOffAt))
                .toList();
    }

}
