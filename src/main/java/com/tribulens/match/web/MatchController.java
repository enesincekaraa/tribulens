package com.tribulens.match.web;


import com.tribulens.match.application.ListMatchesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final ListMatchesService listMatchesService;

    public MatchController(ListMatchesService listMatchesService) {
        this.listMatchesService = listMatchesService;
    }


    @GetMapping
    public List<MatchResponse> listMatches() {
        return listMatchesService.execute()
                .stream()
                .map(MatchResponse::from)
                .toList();
    }
}
