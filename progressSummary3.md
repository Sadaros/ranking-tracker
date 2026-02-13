## MTG Ranking Tracker - API Testing & Error Handling Progress Summary

### Previous Status
From progressSummary2.md: Backend REST API complete with all three layers (controllers, services, repositories) implemented and tested manually via Chrome DevTools. Ready for proper API testing and frontend integration.

### API Testing with Postman
Adopted Postman as the primary API testing tool, replacing ad-hoc JavaScript fetch calls in Chrome DevTools. Key improvements over the previous approach:
- Saved request collections for all endpoints (players, decks, matches)
- Persistent request history with no need to reconstruct JSON bodies manually
- Easier testing of POST requests with complex bodies (e.g. RecordMatchRequest with 5 fields)
- Clear visibility of HTTP status codes and formatted response bodies

Established a systematic testing order based on endpoint dependencies:
1. GET all resources first to verify initial database state
2. Create players, then decks
3. Verify each creation
4. Record matches using IDs from previous steps

Through Postman testing, discovered that all error scenarios (missing fields, invalid data, duplicate names, missing resources) returned generic 500 errors with no useful information for API consumers.

---

### Input Validation (DTO Layer)

Added Jakarta Bean Validation dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Added validation annotations to DTO classes:

**CreatePlayerRequest** - `@NotBlank` on `name` field

**CreateDeckRequest** - `@NotBlank` on `name` field (boolean color fields use Java primitive defaults, so they cannot be null)

**RecordMatchRequest:**
- Changed `result` field from `String` to `MatchResult` enum directly - Spring handles deserialization and automatically rejects invalid values with 400
- Added `@Positive` to all four ID fields (`player1Id`, `player2Id`, `player1DeckId`, `player2DeckId`) to reject zero or negative values
- Added `@Valid` to the controller method parameter to activate validation

**Key validation principle established:** DTO layer validates that the HTTP request is structurally valid (not null, correct format, within bounds). Service layer validates business rules (uniqueness, referential integrity). These responsibilities must not be mixed.

---

### Error Handling Architecture

#### ErrorResponse DTO
Created `ErrorResponse` in the `dto` package to represent a consistent error structure across all endpoints:

```json
{
  "timestamp": "...",
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Human readable explanation",
  "path": "/api/players",
  "invalidFields": {
    "fieldName": "specific error message"
  }
}
```

- `error` - always the HTTP status description ("Bad Request", "Not Found", "Conflict")
- `message` - human readable explanation of what went wrong
- `invalidFields` - field-specific errors when applicable, `null` when not relevant

#### GlobalExceptionHandler
Created `GlobalExceptionHandler` in a new `exception` package using `@ControllerAdvice` - a Spring mechanism that intercepts exceptions thrown by any controller and formats custom responses.

Contains a private `createErrorResponse()` helper method to avoid duplication across handlers.

**Handlers implemented:**

1. **`MethodArgumentNotValidException`** (400 Bad Request)
   - Triggered by `@NotBlank`, `@Positive`, and other Bean Validation failures
   - Extracts field name and message from each `FieldError`
   - Populates `invalidFields` map with field → message pairs

2. **`HttpMessageNotReadableException`** (400 Bad Request)
   - Triggered by malformed JSON or undeserializable values
   - Inspects the cause type to give specific messages:
     - `InvalidFormatException` → extracts field name, invalid value, target type, and accepted enum values if applicable, populates `invalidFields`
     - `StreamReadException` → malformed JSON, returns generic parse error message
     - Unknown cause → logs as warning, returns generic error

3. **`ResourceNotFoundException`** (404 Not Found)
   - Custom exception, thrown explicitly when a player, deck, or match is not found by ID or name
   - Optionally carries a `field` name for cases where the missing resource corresponds to a specific request field

4. **`DuplicateNameException`** (409 Conflict)
   - Custom exception, thrown in service layer before attempting to save when a duplicate name is detected
   - Returns 409 Conflict with a descriptive message

---

### Custom Exceptions

Created in the `exception` package:

**`ResourceNotFoundException`** extends `RuntimeException`
- Two constructors: one with message only, one with message and field name
- Used by `PlayerService`, `DeckService`, and `MatchService` when lookups fail
- Replaces generic `IllegalArgumentException` to avoid accidentally catching unrelated exceptions

**`DuplicateNameException`** extends `RuntimeException`
- Single constructor with message
- Thrown in `PlayerService.createPlayer()` and `DeckService.createDeck()` after checking for existing names via repository before attempting to save

---

### Service Layer Updates

**PlayerService** - checks for existing player by name before creating, throws `DuplicateNameException` if found. Throws `ResourceNotFoundException` instead of `IllegalArgumentException` for not-found cases.

**DeckService** - same duplicate name check pattern as PlayerService.

**MatchService** - wraps each resource lookup in try-catch, rethrowing `ResourceNotFoundException` with the specific field name (e.g. "player2Id") so the error response can identify which ID was invalid.

---

### Logging
Adopted SLF4J logger (`LoggerFactory.getLogger()`) throughout the exception handler. Established log level conventions:
- `logger.error()` - unexpected server-side failures
- `logger.warn()` - client errors worth monitoring
- `logger.info()` - significant normal events
- `logger.debug()` - detailed diagnostic info (disabled by default, configurable in `application.properties`)

---

### Key Concepts Learned

**HTTP Status Codes:**
- 400 Bad Request - structurally invalid request (missing fields, wrong types)
- 404 Not Found - requested resource does not exist
- 409 Conflict - request valid but conflicts with existing data (duplicate name)
- 500 Internal Server Error - unexpected server-side failure

**Validation Separation:**
- DTO layer: structural validity (annotations, types, null checks)
- Service layer: business rule validity (uniqueness, referential integrity)
- Never mix these responsibilities

**Custom Exceptions over Generic Ones:**
- Catching `IllegalArgumentException` globally is dangerous as it can catch unintended exceptions
- Custom exceptions (`ResourceNotFoundException`, `DuplicateNameException`) are specific and safe to catch globally

**@ControllerAdvice Pattern:**
- Single class handles exceptions across all controllers
- Keeps controllers clean - no try-catch blocks needed there
- Centralises error response formatting

---

### Remaining Known Issues / Future Improvements
- Repetitive try-catch blocks in `MatchService.recordMatch()` - candidate for refactoring using `Supplier<T>` lambdas
- No catch-all handler for unexpected exceptions (currently falls back to Spring's default 500 response)
- Automated integration tests not yet written - to revisit after frontend is built

---

**Next Step:** Build the HTML/CSS/JavaScript frontend (steps 6-8 on the roadmap) to consume the REST API.
