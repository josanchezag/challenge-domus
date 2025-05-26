package com.domus.challenge.service;

import com.domus.challenge.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final MovieCacheLoader cacheLoader;

    public List<String> getDirectorsWithThreshold(int threshold) {
        Map<String, Long> directorCount = cacheLoader.getCachedMovies().stream()
                .collect(Collectors.groupingBy(Movie::getDirector, Collectors.counting()));

        return directorCount.entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}