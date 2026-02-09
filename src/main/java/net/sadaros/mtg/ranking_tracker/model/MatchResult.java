package net.sadaros.mtg.ranking_tracker.model;

public enum MatchResult {
    PLAYER1_WIN(1.0, 0.0),
    PLAYER2_WIN(0.0, 1.0),
    DRAW(0.5, 0.5);

    private final double player1Score;
    private final double player2Score;

    MatchResult(double player1Score, double player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public double player1Score() {
        return player1Score;
    }

    public double player2Score() {
        return player2Score;
    }

}
