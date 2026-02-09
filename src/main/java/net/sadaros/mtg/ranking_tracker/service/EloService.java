package net.sadaros.mtg.ranking_tracker.service;

import net.sadaros.mtg.ranking_tracker.model.MatchResult;
import org.springframework.stereotype.Service;

@Service
public class EloService {

    private static final int K_FACTOR =32;

    public UpdatedEloScoreRecord updatedEloScore(int player1Elo, int player2Elo, MatchResult result) {
        ExpectedScore expected = calculateExpectedScore(player1Elo, player2Elo);

        int player1NewRating = (int) (player1Elo + K_FACTOR * (result.player1Score() - expected.player1()));
        int player2NewRating = (int) (player2Elo + K_FACTOR * (result.player2Score() - expected.player2()));

        return new UpdatedEloScoreRecord(player1NewRating, player2NewRating);
    }

    private record ExpectedScore(double player1, double player2) {}

    private ExpectedScore calculateExpectedScore(int player1Elo, int player2Elo) {
        double playerExpectedScore = 1 / (1 + Math.pow(10, ((player2Elo - player1Elo) / 400.0)));
        return new ExpectedScore(playerExpectedScore, 1 - playerExpectedScore);
    }
}

