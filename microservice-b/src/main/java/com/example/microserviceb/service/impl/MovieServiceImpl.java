package com.example.microserviceb.service.impl;

import com.example.microserviceb.client.KinopoiskClientRouter;
import com.example.microserviceb.entity.Movie;
import com.example.microserviceb.exception.custom.ExternalApiException;
import com.example.microserviceb.exception.custom.MovieNotFoundInExternalApiException;
import com.example.microserviceb.repository.MovieRepository;
import com.example.microserviceb.service.MovieService;
import com.example.microserviceb.service.MovieServiceAsync;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final KinopoiskClientRouter kinopoiskClientRouter;
    private final MovieServiceAsync movieServiceAsync ;
    private final ObjectMapper objectMapper;
    @Override
    public Movie getById(Long id) {
        Movie movie = findWithDbById(id)
                .or(() -> findWithApiById(id))
                .orElseThrow(() -> new MovieNotFoundInExternalApiException("Could not find movie with id " + id));
        movieServiceAsync.saveIfNotSaved(movie);
        return movie;
    }
    @Override
    public Movie getByName(String name) {
        Movie movie = movieRepository.findTopByNameContainingIgnoreCase(name)
                .or(() -> findWithApiByName(name))
                .orElseThrow(() -> new MovieNotFoundInExternalApiException("Could not find movie with name" + name));
        movieServiceAsync.saveIfNotSaved(movie);
        return movie;
    }
    @Override
    public List<Movie> getByPageByName(int page, int limit, String name) {
        List<Movie> movieList = findWithApiByPageByName(page, limit, name);
        movieServiceAsync.saveIfNotSaved(movieList);
        return movieList;
    }
    @Override
    public Movie getRandom() {
        Movie movie = kinopoiskClientRouter.findRandom();
        movieServiceAsync.saveIfNotSaved(movie);
        return movie;
    }
    @SneakyThrows
    private Optional<Movie> findWithApiById(Long id) {
        Optional<Movie> movie = Optional.empty();
        try {
            movie = kinopoiskClientRouter.findById(id);
        } catch (FeignException e) {
            handleRawExternalApiException(e);
        }
        return movie;
    }
    @SneakyThrows
    private Optional<Movie> findWithApiByName(String name) {
        Optional<Movie> movie = Optional.empty();
        try {
            movie = Optional.ofNullable(
                    findWithApiByPageByName(1, 1, name)
                            .getFirst()
            );
        } catch (FeignException e) {
            handleRawExternalApiException(e);
        }
        return movie;
    }
    private List<Movie> findWithApiByPageByName(int page, int limit, String name) {
        return kinopoiskClientRouter.findByPageByName(page, limit, name).getMovies();
    }
    private Optional<Movie> findWithDbById(Long id) {
        return movieRepository.findById(id);
    }
    private void handleRawExternalApiException(FeignException e) throws JsonProcessingException, ExternalApiException {
        Map<String, Object> map = objectMapper.readValue(e.contentUTF8(), new TypeReference<>() {
        });
        //noinspection unchecked
        throw new ExternalApiException(
                String.join(", ", (List<String>) map.get("message")),
                (int) map.get("statusCode")
        );
    }
}
