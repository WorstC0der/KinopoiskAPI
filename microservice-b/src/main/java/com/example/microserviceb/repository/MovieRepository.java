package com.example.microserviceb.repository;

import com.example.microserviceb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findTopByNameContainingIgnoreCase(String name);

    List<Movie> findByIdIn(List<Long> movieIds);
}
