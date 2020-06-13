package com.example.FilmScrape.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "WikiFilmModel")
@NoArgsConstructor
@AllArgsConstructor
public class WikiFilmModel {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private String year;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "directors")
    private String directors;

    @Column(name = "stars")
    private String stars;

    @Column(name = "gross")
    private String gross;

    @Column(name = "information")
    private String information;
}
