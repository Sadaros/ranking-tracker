package net.sadaros.mtg.ranking_tracker.service;

import net.sadaros.mtg.ranking_tracker.exception.ResourceNotFoundException;
import net.sadaros.mtg.ranking_tracker.model.Deck;
import net.sadaros.mtg.ranking_tracker.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck createDeck(String name, boolean white, boolean blue,
                           boolean black, boolean red, boolean green) {
        Deck deck = new Deck(name, white, blue, black, red, green);
        return deckRepository.save(deck);
    }

    public Deck getDeck(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
    }

    public Deck getDeckByName(String name) {
        return deckRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No deck named " + name + " found"));
    }

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }
}
