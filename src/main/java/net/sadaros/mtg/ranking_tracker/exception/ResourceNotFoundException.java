package net.sadaros.mtg.ranking_tracker.exception;

import jakarta.annotation.Nullable;

public class ResourceNotFoundException extends RuntimeException {

    @Nullable
    private String field;

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, @Nullable String field) {
        super(message);
        this.field = field;
    }

    @Nullable
    public String getField() {
        return field;
    }

    public void setField(@Nullable String field) {
        this.field = field;
    }
}