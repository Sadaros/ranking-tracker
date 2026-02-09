package net.sadaros.mtg.ranking_tracker.repository;

import net.sadaros.mtg.ranking_tracker.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
