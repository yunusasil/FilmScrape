package com.example.FilmScrape.Repository;

import com.example.FilmScrape.Model.WikiFilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiFilmRepository extends JpaRepository<WikiFilmModel, Long> {
}
