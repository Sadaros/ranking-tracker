package net.sadaros.mtg.ranking_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="player1_id", nullable = false)
    private Player player1;


    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    private Player player2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchResult result;

    @ManyToOne
    @JoinColumn(name = "player1_deck", nullable = false)
    private Deck player1Deck;

    @ManyToOne
    @JoinColumn(name = "player2_deck", nullable = false)
    private Deck player2Deck;

    @Column(nullable = false)
    private Integer player1EloBefore;

    @Column(nullable = false)
    private Integer player2EloBefore;

    @Column(nullable = false)
    private Integer player1EloAfter;

    @Column(nullable = false)
    private Integer player2EloAfter;

    @Column(nullable = false)
    private LocalDateTime playedAt = LocalDateTime.now();



    // Constructors
    public Match() {

    }

    public Match(Player player1, Player player2, MatchResult result, Deck player1Deck, Deck player2Deck,
                 Integer player1EloBefore, Integer player2EloBefore,
                 Integer player1EloAfter, Integer player2EloAfter) {
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
        this.player1Deck = player1Deck;
        this.player2Deck = player2Deck;
        this.player1EloBefore = player1EloBefore;
        this.player2EloBefore = player2EloBefore;
        this.player1EloAfter = player1EloAfter;
        this.player2EloAfter = player2EloAfter;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setWinner(MatchResult result) {
        this.result = result;
    }

    public Integer getPlayer1EloBefore() {
        return player1EloBefore;
    }

    public void setPlayer1EloBefore(Integer player1EloBefore) {
        this.player1EloBefore = player1EloBefore;
    }

    public Integer getPlayer2EloBefore() {
        return player2EloBefore;
    }

    public void setPlayer2EloBefore(Integer player2EloBefore) {
        this.player2EloBefore = player2EloBefore;
    }

    public Integer getPlayer1EloAfter() {
        return player1EloAfter;
    }

    public void setPlayer1EloAfter(Integer player1EloAfter) {
        this.player1EloAfter = player1EloAfter;
    }

    public Integer getPlayer2EloAfter() {
        return player2EloAfter;
    }

    public void setPlayer2EloAfter(Integer player2EloAfter) {
        this.player2EloAfter = player2EloAfter;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public Deck getPlayer1Deck() {
        return player1Deck;
    }

    public void setPlayer1Deck(Deck player1Deck) {
        this.player1Deck = player1Deck;
    }

    public Deck getPlayer2Deck() {
        return player2Deck;
    }

    public void setPlayer2Deck(Deck player2Deck) {
        this.player2Deck = player2Deck;
    }
}


