package net.sadaros.mtg.ranking_tracker.service;

import net.sadaros.mtg.ranking_tracker.model.Deck;
import net.sadaros.mtg.ranking_tracker.model.Match;
import net.sadaros.mtg.ranking_tracker.model.MatchResult;
import net.sadaros.mtg.ranking_tracker.model.Player;
import net.sadaros.mtg.ranking_tracker.repository.MatchRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerService playerService;
    private final EloService eloService;
    private final DeckService deckService;

    public  MatchService(
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

    public void recordMatch(
            long player1Id, long player2Id, MatchResult result,
            long player1DeckId, long player2DeckId
    ) {
        Player player1 = playerService.getPlayer(player1Id);
        Player player2 = playerService.getPlayer(player2Id);
        Deck player1Deck = deckService.getDeck(player1DeckId);
        Deck player2Deck = deckService.getDeck(player2DeckId);
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

        matchRepository.save(match);
        playerService.updatePlayer(player1);
        playerService.updatePlayer(player2);
    }
}
