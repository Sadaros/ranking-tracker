package net.sadaros.mtg.ranking_tracker.controller;

import net.sadaros.mtg.ranking_tracker.dto.RecordMatchRequest;
import net.sadaros.mtg.ranking_tracker.model.Match;
import net.sadaros.mtg.ranking_tracker.model.MatchResult;
import net.sadaros.mtg.ranking_tracker.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @PostMapping
    public Match recordMatch(@RequestBody RecordMatchRequest request) {
        MatchResult result = MatchResult.valueOf(request.getResult().toUpperCase());
        return matchService.recordMatch(
                request.getPlayer1Id(), request.getPlayer2Id(), result,
                request.getPlayer1DeckId(), request.getPlayer2DeckId()
        );
    }
}
