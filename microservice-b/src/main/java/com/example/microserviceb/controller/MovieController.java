package com.example.microserviceb.controller;

import com.example.microserviceb.entity.Movie;
import com.example.microserviceb.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${application.base-url}")
public class MovieController {

    private MovieService movieService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getById(@PathVariable("id") Long id) {
        return movieService.getById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Movie getByName(
            @RequestParam(value = "name") String name
    ) {
        return movieService.getByName(name);
    }

    @GetMapping("/searchList")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "name", required = false) String name
    ) {
        return movieService.getByPageByName(page, limit, name);
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public Movie getRandom() {
        return movieService.getRandom();
    }
}