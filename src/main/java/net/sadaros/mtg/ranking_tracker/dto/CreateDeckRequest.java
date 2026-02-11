package net.sadaros.mtg.ranking_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateDeckRequest {

    @NotBlank(message = "Deck name is required, and cannot be blank")
    private String name;
    private boolean white;
    private boolean blue;
    private boolean black;
    private boolean red;
    private boolean green;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }
}
