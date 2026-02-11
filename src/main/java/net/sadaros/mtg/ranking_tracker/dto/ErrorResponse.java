package net.sadaros.mtg.ranking_tracker.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timeStamp;
    private int statusCode;
    private String error;
    private String path;
    private Map<String, String> invalidFields;

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timeStamp=" + timeStamp +
                ", statusCode=" + statusCode +
                ", error='" + error + '\'' +
                ", path='" + path + '\'' +
                ", invalidFields=" + invalidFields +
                '}';
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(Map<String, String> invalidFields) {
        this.invalidFields = invalidFields;
    }
}
