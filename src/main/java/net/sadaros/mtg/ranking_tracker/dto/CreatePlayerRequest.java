package net.sadaros.mtg.ranking_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePlayerRequest {

    @NotBlank(message = "Player name is required, and can't be blank.")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
