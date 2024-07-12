package com.example.microservicea.client;

import com.example.microservicea.entity.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-a", url = "${microservice-b.base-url}")
public interface KinopoiskClient {
    @GetMapping("/{id}")
    Movie findById(@PathVariable("id") Long id);

    @GetMapping("")
    List<Movie> findByPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    );

    @GetMapping("/search")
    Movie findByName(
            @RequestParam(value = "name") String name
    );

    @GetMapping("/searchList")
    List<Movie> findByPageByName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "name", required = false) String name
    );

    @GetMapping("/random")
    Movie findRandom();
}
