package com.example.microserviceb.service;

import com.example.microserviceb.entity.Movie;

import java.util.List;
public interface MovieService {
    Movie getById(Long id);

    Movie getByName(String name);

    List<Movie> getByPageByName(int page, int limit, String name);

    Movie getRandom();
}
