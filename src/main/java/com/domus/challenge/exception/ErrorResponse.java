package com.domus.challenge.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Standard error response format")
public class ErrorResponse {

    @Schema(description = "Timestamp when the error occurred", example = "2025-05-25T14:32:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "HTTP status message", example = "Bad Request")
    private String error;

    @Schema(description = "Application-specific error code", example = "INVALID_THRESHOLD")
    private ErrorCode code;

    @Schema(description = "Human-readable error message", example = "Threshold must be a non-negative integer")
    private String message;
}

