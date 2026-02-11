package net.sadaros.mtg.ranking_tracker.controller;


import jakarta.validation.Valid;
import net.sadaros.mtg.ranking_tracker.dto.CreateDeckRequest;
import net.sadaros.mtg.ranking_tracker.model.Deck;
import net.sadaros.mtg.ranking_tracker.service.DeckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> getAllDecks() {
        return deckService.getAllDecks();
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable Long id) {
        return deckService.getDeck(id);
    }

    @GetMapping("/search")
    public Deck getDeckByName(@RequestParam String name) {
        return deckService.getDeckByName(name);
    }

    @PostMapping
    public Deck createDeck(@RequestBody @Valid CreateDeckRequest request) {
        return deckService.createDeck(
                request.getName(),
                request.isWhite(),
                request.isBlue(),
                request.isBlack(),
                request.isRed(),
                request.isGreen()
        );
    }
}
