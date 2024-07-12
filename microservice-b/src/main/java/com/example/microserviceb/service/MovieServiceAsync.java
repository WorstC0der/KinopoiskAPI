package com.example.microserviceb.service;

import com.example.microserviceb.entity.Movie;

import java.util.List;

public interface MovieServiceAsync {
    void saveIfNotSaved(List<Movie> movieList);

    void saveIfNotSaved(Movie movie);
}
