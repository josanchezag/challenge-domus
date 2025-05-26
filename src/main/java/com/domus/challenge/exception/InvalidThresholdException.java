package com.domus.challenge.exception;

/**
 * Exception thrown when the threshold query parameter is invalid.
 */
public class InvalidThresholdException extends RuntimeException {
    public InvalidThresholdException(String message) {
        super(message);
    }
}
