package net.sadaros.mtg.ranking_tracker.dto;

public class RecordMatchRequest {
    private long player1Id;
    private long player2Id;
    private String result;
    private long player1DeckId;
    private long player2DeckId;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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
