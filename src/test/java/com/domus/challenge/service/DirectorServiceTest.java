package com.domus.challenge.service;

import com.domus.challenge.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @Mock
    private MovieCacheLoader cacheLoader;

    @InjectMocks
    private DirectorService directorService;

    private Movie movie(String title, String director) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setDirector(director);
        return m;
    }

    @Test
    void shouldReturnDirectorsAboveThresholdSortedAlphabetically() {
        List<Movie> movies = List.of(
                movie("Movie 1", "Scorsese"),
                movie("Movie 2", "Scorsese"),
                movie("Movie 3", "Allen")
        );
        when(cacheLoader.getCachedMovies()).thenReturn(movies);

        List<String> result = directorService.getDirectorsWithThreshold(1);

        assertEquals(List.of("Scorsese"), result);
    }

    @Test
    void shouldReturnEmptyListForHighThreshold() {
        List<Movie> movies = List.of(
                movie("Movie 1", "Scorsese"),
                movie("Movie 2", "Allen")
        );
        when(cacheLoader.getCachedMovies()).thenReturn(movies);

        List<String> result = directorService.getDirectorsWithThreshold(5);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListForNegativeThreshold() {
        when(cacheLoader.getCachedMovies()).thenReturn(List.of());

        List<String> result = directorService.getDirectorsWithThreshold(-1);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfMoviesAreEmpty() {
        when(cacheLoader.getCachedMovies()).thenReturn(List.of());

        List<String> result = directorService.getDirectorsWithThreshold(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfMoviesAreNull() {
        when(cacheLoader.getCachedMovies()).thenReturn(null);

        List<String> result = directorService.getDirectorsWithThreshold(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldIgnoreMoviesWithNullDirector() {
        List<Movie> movies = List.of(
                movie("Movie 1", "Scorsese"),
                movie("Movie 2", null)
        );
        when(cacheLoader.getCachedMovies()).thenReturn(movies);

        List<String> result = directorService.getDirectorsWithThreshold(0);

        assertEquals(List.of("Scorsese"), result);
    }
}

