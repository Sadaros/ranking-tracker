package net.sadaros.mtg.ranking_tracker.repository;

import net.sadaros.mtg.ranking_tracker.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>{
}
