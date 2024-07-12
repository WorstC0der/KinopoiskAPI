package com.example.microservicea.controller;

import com.example.microservicea.client.KinopoiskClient;
import com.example.microservicea.entity.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${application.base-url}")
public class MovieController {

    private KinopoiskClient kinopoiskClient;

    @Operation(summary = "Получить фильм по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Найденный фильм",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
    @GetMapping("/{id}")
    public Movie getById(@Parameter(description = "ID фильма") @PathVariable("id") Long id) {
        return kinopoiskClient.findById(id);
    }

    @Operation(summary = "Поиск фильма по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Найденный фильм",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
    @GetMapping("/search")
    public Movie getByName(
            @Parameter(description = "Название фильма") @RequestParam(value = "name") String name
    ) {
        return kinopoiskClient.findByName(name);
    }

    @Operation(summary = "Список фильмов по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список фильмов",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
    @GetMapping("/searchList")
    public List<Movie> getByPageByName(
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Parameter(description = "Количество элементов на странице") @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @Parameter(description = "Название фильма") @RequestParam(value = "name", required = false) String name
    ) {
        return kinopoiskClient.findByPageByName(page, limit, name);
    }

    @Operation(summary = "Получить рандомный тайтл")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Найденный фильм",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
    @GetMapping("/random")
    public Movie getRandom() {
        return kinopoiskClient.findRandom();
    }
}
