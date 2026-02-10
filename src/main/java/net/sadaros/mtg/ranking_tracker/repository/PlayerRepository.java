package net.sadaros.mtg.ranking_tracker.repository;

import net.sadaros.mtg.ranking_tracker.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long>{

    Optional<Player> findByName(String name);
}
