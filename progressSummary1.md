## MTG Ranking Tracker - Backend Progress Summary

### Project Setup
- Created Spring Boot project with Maven
- Added dependencies: Spring Web, Spring Data JPA, SQLite JDBC, Hibernate Community Dialects
- Configured SQLite database in `application.properties`

### Data Model (Entities)
Created three entities with JPA annotations:

1. **Player** - tracks name, elo rating, wins, losses, draws, creation timestamp
2. **Match** - records match results with both players, their decks, elo before/after, result, and timestamp
   - Uses `@ManyToOne` relationships to link to Player and Deck entities
3. **Deck** - stores deck name and MTG color identity (white, blue, black, red, green)

### Enums
**MatchResult** - PLAYER1_WIN, PLAYER2_WIN, DRAW with associated scores for Elo calculation

### Repository Layer
Created JPA repositories extending `JpaRepository<Entity, Long>`:
- `PlayerRepository`
- `MatchRepository`
- `DeckRepository`

These provide database access methods (findAll, save, findById, etc.) automatically.

### Service Layer
1. **EloService** - calculates updated Elo ratings given two player ratings and a match result (K-factor: 32)
2. **PlayerService** - manages player operations (create, get, getAll)
3. **DeckService** - manages deck operations (create, get, getAll)
4. **MatchService** - coordinates match recording: fetches players/decks, calculates Elo changes, updates win/loss/draw counts, saves match and updated players

### Architecture
Following layered architecture: Controller → Service → Repository → Database

---

**Next step:** Build REST API controllers to expose these services via HTTP endpoints.