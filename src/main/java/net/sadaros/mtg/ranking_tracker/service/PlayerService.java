package net.sadaros.mtg.ranking_tracker.service;


import net.sadaros.mtg.ranking_tracker.exception.DuplicateNameException;
import net.sadaros.mtg.ranking_tracker.exception.ResourceNotFoundException;
import net.sadaros.mtg.ranking_tracker.model.Player;
import net.sadaros.mtg.ranking_tracker.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(String name) {
        if (playerRepository.findByName(name).isPresent()) {
            throw new DuplicateNameException("A player with name " + name + " already exists");
        }
        Player player = new Player(name);
        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    }

    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No player named " + name + " found"));
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
