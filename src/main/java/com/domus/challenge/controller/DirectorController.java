package com.domus.challenge.controller;

import com.domus.challenge.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
@Tag(name = "Directors", description = "Operations related to movie directors")
public class DirectorController {

    private final DirectorService directorService;

    @Operation(summary = "Get directors with movie count above threshold",
            description = "Returns a list of director names whose number of directed movies is strictly greater than the given threshold.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Invalid threshold value",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "External API error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getDirectors(
            @Parameter(description = "Minimum number of movies a director must have directed")
            @RequestParam("threshold") String thresholdParam) {

        try {
            int threshold = Integer.parseInt(thresholdParam);
            if (threshold < 0) return ResponseEntity.ok(Map.of("directors", List.of()));

            List<String> directors = directorService.getDirectorsWithThreshold(threshold);
            Map<String, Object> response = new HashMap<>();
            response.put("directors", directors);
            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Threshold must be a number"));
        }
    }
}
