package com.example.FilmScrape.Service;

import com.example.FilmScrape.Model.WikiFilmModel;

import java.io.IOException;
import java.util.List;

public interface WikiFilmService {
    List<WikiFilmModel> getAllWikiFilms();

    WikiFilmModel getWikiFilmById(Long id);

    void saveWikiFilm(WikiFilmModel wikiFilmModel);

    void deleteWikiFilm(WikiFilmModel wikiFilmModel);

    void scrapeData() throws IOException;
}
