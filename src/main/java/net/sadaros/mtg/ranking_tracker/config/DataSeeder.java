package net.sadaros.mtg.ranking_tracker.config;

import net.sadaros.mtg.ranking_tracker.model.MatchResult;
import net.sadaros.mtg.ranking_tracker.service.DeckService;
import net.sadaros.mtg.ranking_tracker.service.MatchService;
import net.sadaros.mtg.ranking_tracker.service.PlayerService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final PlayerService playerService;
    private final DeckService deckService;
    private final MatchService matchService;

    public DataSeeder(PlayerService playerService, DeckService deckService, MatchService matchService) {
        this.playerService = playerService;
        this.deckService = deckService;
        this.matchService = matchService;
    }

    @Override
    public void run(String @NonNull ... args) {

        if(!playerService.getAllPlayers().isEmpty()) {
            System.out.println("Database already has data, skipping seed.");
            return;
        }

        System.out.println("Seeding database...");

        var bob = playerService.createPlayer("Bob");
        var alice = playerService.createPlayer("Alice");
        var johnny = playerService.createPlayer("Johnny");
        var kate = playerService.createPlayer("Kate");

        var monoRed = deckService.createDeck("Mono Red Burn", false, false, false, true, false);
        var fourColor = deckService.createDeck("Four colour control", true, true, true, true, false);
        var jund = deckService.createDeck("Jund", false, false, true, true, true);
        var rubyMedallion = deckService.createDeck("Ruby Medallion", false, false, false, true, false);

        matchService.recordMatch(bob.getId(), alice.getId(), MatchResult.PLAYER1_WIN, monoRed.getId(), fourColor.getId());
        matchService.recordMatch(johnny.getId(), kate.getId(), MatchResult.PLAYER2_WIN, jund.getId(), rubyMedallion.getId());
        matchService.recordMatch(bob.getId(), johnny.getId(), MatchResult.PLAYER1_WIN, monoRed.getId(), jund.getId());
        matchService.recordMatch(alice.getId(), kate.getId(), MatchResult.DRAW, fourColor.getId(), rubyMedallion.getId());

        System.out.println("Database seeded successfully!");
    }
}
