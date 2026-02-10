package net.sadaros.mtg.ranking_tracker.repository;

import net.sadaros.mtg.ranking_tracker.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findByName(String name);
}
