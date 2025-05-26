package com.domus.challenge.exception;

import lombok.Getter;

/**
 * Enum representing application-specific error codes and descriptions.
 */
@Getter
public enum ErrorCode {

    INVALID_THRESHOLD("Threshold must be a non-negative integer."),
    EXTERNAL_API_FAILURE("Failed to retrieve data from external movie API."),
    VALIDATION_ERROR("Validation failed for the provided input."),
    INVALID_INPUT("The provided input is invalid."),
    UNKNOWN_ERROR("An unexpected error occurred.");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

}
