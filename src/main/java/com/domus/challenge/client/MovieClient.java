package com.domus.challenge.client;

import com.domus.challenge.exception.ExternalApiException;
import com.domus.challenge.model.Movie;
import com.domus.challenge.model.MoviePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieClient {

    @Value("${external.api.endpoint}")
    private String endpoint;

    private final WebClient webClient = WebClient.create();

    public List<Movie> fetchAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        int totalPages = Integer.MAX_VALUE;

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            final int page = currentPage;
            try {
                String url = endpoint.replace("<pageNumber>", String.valueOf(page));
                MoviePageResponse response = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(MoviePageResponse.class)
                        .onErrorResume(e -> {
                            log.error("Error fetching page {}: {}", page, e.getMessage());
                            throw new ExternalApiException("Failed to fetch movies from external API");
                        })
                        .block();

                if (response != null && response.getData() != null) {
                    allMovies.addAll(response.getData());
                    totalPages = response.getTotalPages();
                    log.debug("Successfully fetched page {} of {}", page, totalPages);
                } else {
                    log.warn("Empty response received for page {}", page);
                    break;
                }
            } catch (ExternalApiException e) {
                log.error("Failed to fetch movies from external API: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("Unexpected error while fetching movies: {}", e.getMessage());
                throw new ExternalApiException("Unexpected error while fetching movies");
            }
        }

        return allMovies;
    }
}
