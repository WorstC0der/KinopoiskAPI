package com.example.microserviceb.client;

import com.example.microserviceb.entity.Movie;
import com.example.microserviceb.entity.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "KinopoiskClientRouter", url = "${kinopoisk-api.base-url}", configuration = FeignConfig.class)
public interface  KinopoiskClientRouter {
    @GetMapping("/v1.4/movie/{id}")
    Optional<Movie> findById(@PathVariable("id") Long id);

    @GetMapping("/v1.4/movie/search")
    Page findByPageByName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "query", required = false) String name
    );

    @GetMapping("/v1.4/movie/random")
    Movie findRandom();
}
