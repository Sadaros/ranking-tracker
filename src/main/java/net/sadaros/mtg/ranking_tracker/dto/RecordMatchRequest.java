package net.sadaros.mtg.ranking_tracker.dto;

import jakarta.validation.constraints.Positive;
import net.sadaros.mtg.ranking_tracker.model.MatchResult;

public class RecordMatchRequest {

    @Positive
    private long player1Id;
    @Positive
    private long player2Id;
    @Positive
    private long player1DeckId;
    @Positive
    private long player2DeckId;
    private MatchResult result;

    public long getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(long player1Id) {
        this.player1Id = player1Id;
    }

    public long getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(long player2Id) {
        this.player2Id = player2Id;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public long getPlayer1DeckId() {
        return player1DeckId;
    }

    public void setPlayer1DeckId(long player1DeckId) {
        this.player1DeckId = player1DeckId;
    }

    public long getPlayer2DeckId() {
        return player2DeckId;
    }

    public void setPlayer2DeckId(long player2DeckId) {
        this.player2DeckId = player2DeckId;
    }
}
