package com.example.microservicea.entity;

import lombok.Data;

@Data
public class Movie {
    private Long id;
    private String name;
    private String alternativeName;
    private String enName;
    private String type;
    private Integer year;
    private String description;
    private String shortDescription;
    private String slogan;
    private String status;
    private Double rating_kp;
    private Integer movieLength;
    private Integer ageRating;
}
