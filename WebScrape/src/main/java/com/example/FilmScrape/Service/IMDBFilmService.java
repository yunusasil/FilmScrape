package com.example.FilmScrape.Service;

import com.example.FilmScrape.Model.IMDBFilmModel;

import java.io.IOException;
import java.util.List;

public interface IMDBFilmService {

    List<IMDBFilmModel> getIMDBFilmAll();

    IMDBFilmModel getIMDBFilmById(Long id);

    void saveIMDBFilm(IMDBFilmModel IMDBFilmModel);

    void deleteIMDBFilm(IMDBFilmModel IMDBFilmModel);

    void scrapeData() throws IOException;
}