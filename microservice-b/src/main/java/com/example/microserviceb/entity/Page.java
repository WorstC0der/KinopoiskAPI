package com.example.microserviceb.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Page {
    @JsonProperty("docs")
    private List<Movie> movies;

    private int total;

    private int limit;

    private int page;

    private int pages;
}
