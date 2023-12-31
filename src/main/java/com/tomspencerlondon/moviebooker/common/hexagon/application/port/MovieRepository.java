package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import java.util.List;
import java.util.Optional;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;

public interface MovieRepository {
    Movie save(Movie movie);
    List<Movie> findAll();

    Optional<Movie> findById(Long movieId);
}
