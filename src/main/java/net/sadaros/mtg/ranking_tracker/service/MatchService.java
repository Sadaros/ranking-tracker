package net.sadaros.mtg.ranking_tracker.service;

import net.sadaros.mtg.ranking_tracker.exception.ResourceNotFoundException;
import net.sadaros.mtg.ranking_tracker.model.Deck;
import net.sadaros.mtg.ranking_tracker.model.Match;
import net.sadaros.mtg.ranking_tracker.model.MatchResult;
import net.sadaros.mtg.ranking_tracker.model.Player;
import net.sadaros.mtg.ranking_tracker.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerService playerService;
    private final EloService eloService;
    private final DeckService deckService;

    public MatchService(
            MatchRepository matchRepository,
            PlayerService playerService,
            EloService eloService,
            DeckService deckService
    ) {
        this.matchRepository = matchRepository;
        this.eloService = eloService;
        this.playerService = playerService;
        this.deckService = deckService;
    }

    public Match recordMatch(
            long player1Id, long player2Id, MatchResult result,
            long player1DeckId, long player2DeckId
    ) {
        Player player1;
        Player player2;
        Deck player1Deck;
        Deck player2Deck;
        try {
            player1 = playerService.getPlayer(player1Id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage(), "player1Id");
        }
        try {
            player2 = playerService.getPlayer(player2Id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage(), "player2Id");
        }
        try {
            player1Deck = deckService.getDeck(player1DeckId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage(), "player1DeckId");
        }
        try {
            player2Deck = deckService.getDeck(player2DeckId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage(), "player2DeckId");
        }
        int player1Elo = player1.getEloRating();
        int player2Elo = player2.getEloRating();
        UpdatedEloScoreRecord newEloScores = eloService.updatedEloScore(player1Elo, player2Elo, result);
        int player1NewElo = newEloScores.player1();
        int player2NewElo = newEloScores.player2();


        switch (result) {
            case DRAW -> {
                player1.setDraws(player1.getDraws() + 1);
                player2.setDraws(player2.getDraws() + 1);
            }
            case PLAYER1_WIN -> {
                player1.setWins(player1.getWins() + 1);
                player2.setLosses(player2.getLosses() + 1);
            }
            case PLAYER2_WIN -> {
                player1.setLosses(player1.getLosses() + 1);
                player2.setWins(player2.getWins() + 1);
            }
        }

        player1.setEloRating(player1NewElo);
        player2.setEloRating(player2NewElo);

        Match match = new Match(
                player1, player2, result,
                player1Deck, player2Deck,
                player1Elo, player2Elo,
                player1NewElo, player2NewElo
        );

        playerService.updatePlayer(player1);
        playerService.updatePlayer(player2);
        return matchRepository.save(match);
    }

    public Match getMatch(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
}
