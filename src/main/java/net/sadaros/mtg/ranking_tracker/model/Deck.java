package net.sadaros.mtg.ranking_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean white;
    @Column(nullable = false)
    private Boolean blue;
    @Column(nullable = false)
    private Boolean black;
    @Column(nullable = false)
    private Boolean red;
    @Column(nullable = false)
    private Boolean green;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    // Constructors
    public Deck() {
    }

    public Deck(String name, boolean hasWhite, boolean hasBlue,
                boolean hasBlack, boolean hasRed, boolean hasGreen) {
        this.name = name;
        this.white = hasWhite;
        this.blue = hasBlue;
        this.black = hasBlack;
        this.red = hasRed;
        this.green = hasGreen;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWhite() {
        return white;
    }
    public void setWhite(Boolean white) {
        this.white = white;
    }
    public Boolean getBlue() {
        return blue;
    }
    public void setBlue(Boolean blue) {
        this.blue = blue;
    }
    public Boolean getBlack() {
        return black;
    }
    public void setBlack(Boolean black) {
        this.black = black;
    }
    public Boolean getRed() {
        return red;
    }
    public void setRed(Boolean red) {
        this.red = red;
    }
    public Boolean getGreen() {
        return green;
    }
    public void setGreen(Boolean green) {
        this.green = green;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
