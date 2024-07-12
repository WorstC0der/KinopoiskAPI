package com.example.microserviceb.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @EqualsAndHashCode.Exclude
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
