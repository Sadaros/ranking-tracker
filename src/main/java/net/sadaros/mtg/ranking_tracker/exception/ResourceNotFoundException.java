package net.sadaros.mtg.ranking_tracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}