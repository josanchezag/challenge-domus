package com.domus.challenge.exception;

/**
 * Exception thrown when the external movie API cannot be reached or returns an error.
 */
public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }
}
