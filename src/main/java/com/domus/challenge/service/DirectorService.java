package com.domus.challenge.service;

import com.domus.challenge.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final MovieCacheLoader cacheLoader;

    public List<String> getDirectorsWithThreshold(int threshold) {
        List<Movie> movies = Optional.ofNullable(cacheLoader.getCachedMovies())
                .orElse(List.of());

        if (movies.isEmpty() || threshold < 0) {
            return List.of();
        }

        return movies.stream()
                .map(Movie::getDirector)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(director -> director, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
}