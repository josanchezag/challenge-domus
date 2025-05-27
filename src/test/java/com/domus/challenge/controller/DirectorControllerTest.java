package com.domus.challenge.controller;

import com.domus.challenge.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(DirectorController.class)
class DirectorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DirectorService directorService;

    @Test
    void getDirectors_WithValidThreshold_ShouldReturnDirectors() {
        // Arrange
        List<String> expectedDirectors = Arrays.asList("Martin Scorsese", "Woody Allen");
        when(directorService.getDirectorsWithThreshold(anyInt()))
                .thenReturn(expectedDirectors);

        // Act & Assert
        webTestClient.get()
                .uri("/api/directors?threshold=4")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.directors").isArray()
                .jsonPath("$.directors[0]").isEqualTo("Martin Scorsese")
                .jsonPath("$.directors[1]").isEqualTo("Woody Allen");
    }

    @Test
    void getDirectors_WithNegativeThreshold_ShouldReturnEmptyList() {
        // Arrange
        when(directorService.getDirectorsWithThreshold(anyInt()))
                .thenReturn(List.of());

        // Act & Assert
        webTestClient.get()
                .uri("/api/directors?threshold=-1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.directors").isArray()
                .jsonPath("$.directors").isEmpty();
    }

    @Test
    void getDirectors_WithInvalidThreshold_ShouldReturnBadRequest() {
        // Act & Assert
        webTestClient.get()
                .uri("/api/directors?threshold=abc")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getDirectors_WithMissingThreshold_ShouldReturnBadRequest() {
        // Act & Assert
        webTestClient.get()
                .uri("/api/directors")
                .exchange()
                .expectStatus().isBadRequest();
    }
} 