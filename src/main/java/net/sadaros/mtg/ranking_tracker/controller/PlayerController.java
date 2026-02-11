package net.sadaros.mtg.ranking_tracker.controller;

import jakarta.validation.Valid;
import net.sadaros.mtg.ranking_tracker.dto.CreatePlayerRequest;
import net.sadaros.mtg.ranking_tracker.model.Player;
import net.sadaros.mtg.ranking_tracker.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @GetMapping("/search")
    public Player getPlayerByName(@RequestParam String name) {
        return playerService.getPlayerByName(name);
    }

    @PostMapping
    public Player createPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        return playerService.createPlayer(request.getName());
    }
}
