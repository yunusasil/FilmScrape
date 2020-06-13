package com.example.FilmScrape.Repository;

import com.example.FilmScrape.Model.IMDBFilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMDBFilmRepository extends JpaRepository<IMDBFilmModel, Long> {
}