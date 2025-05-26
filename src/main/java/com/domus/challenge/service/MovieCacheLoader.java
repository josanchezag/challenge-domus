package com.domus.challenge.service;

import com.domus.challenge.client.MovieClient;
import com.domus.challenge.model.Movie;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieCacheLoader {

    private final MovieClient movieClient;
    private final AtomicReference<List<Movie>> cachedMovies = new AtomicReference<>(Collections.emptyList());

    @PostConstruct
    public void loadOnStartup() {
        log.info("Loading movies at startup...");
        refreshMovieCache();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void refreshMovieCache() {
        try {
            List<Movie> allMovies = movieClient.fetchAllMovies();
            cachedMovies.set(allMovies);
            log.info("Movie cache refreshed: {} movies loaded", allMovies.size());
        } catch (Exception e) {
            log.error("Failed to refresh movie cache", e);
        }
    }

    public List<Movie> getCachedMovies() {
        return cachedMovies.get();
    }
}