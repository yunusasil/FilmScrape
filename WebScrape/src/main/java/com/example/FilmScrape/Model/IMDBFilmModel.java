package com.example.FilmScrape.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "IMDBFilmModel")
@NoArgsConstructor
@AllArgsConstructor
public class IMDBFilmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "genre")
    private String genre;

    @Column(name = "imdb_point")
    private Double imdbPoint;

    @Column(name = "directors")
    private String directors;

    @Column(name = "stars")
    private String stars;

    @Column(name = "gross")
    private String gross;

    @Column(name = "information")
    private String information;

}