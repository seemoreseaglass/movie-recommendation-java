package com.seemoreseaglass.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "title_basics")
public class Title {
    @Id
    private String tconst;

    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;
    private String genres;
}