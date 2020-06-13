package com.example.FilmScrape.Service.Impl;

import com.example.FilmScrape.Model.IMDBFilmModel;
import com.example.FilmScrape.Repository.IMDBFilmRepository;
import com.example.FilmScrape.Service.IMDBFilmService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IMDBFilmServiceImp implements IMDBFilmService {

    private static final String LIST_URL = "https://www.imdb.com/list/ls005750764/?sort=list_order,asc&st_dt=&mode=detail&page=";
    private static final String IMDB_URL = "https://www.imdb.com";

    private final IMDBFilmRepository IMDBFilmRepository;

    @Autowired
    public IMDBFilmServiceImp(IMDBFilmRepository IMDBFilmRepository) {
        this.IMDBFilmRepository = IMDBFilmRepository;
    }

    @Override
    public List<IMDBFilmModel> getIMDBFilmAll() {
        return IMDBFilmRepository.findAll();
    }

    @Override
    public IMDBFilmModel getIMDBFilmById(Long id){
        Optional<IMDBFilmModel> optFilm = IMDBFilmRepository.findById(id);
        return optFilm.get();
    }

    @Override
    public void saveIMDBFilm(IMDBFilmModel IMDBFilmModel) {
        IMDBFilmRepository.save(IMDBFilmModel);
    }

    @Override
    public void deleteIMDBFilm(IMDBFilmModel IMDBFilmModel) {
        IMDBFilmRepository.delete(IMDBFilmModel);
    }

    @Override
    public void scrapeData() throws IOException {
        List<IMDBFilmModel> filmModelList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            Document document = Jsoup.connect(LIST_URL + i)
                    .timeout(70000)
                    .maxBodySize(1024 * 1024 * 10)
                    .get();
            for (Element row : document.getElementsByClass("lister-item-content")) {
                IMDBFilmModel IMDBFilmModel = new IMDBFilmModel();
                IMDBFilmModel.setName(row.getElementsByClass("lister-item-header").get(0).child(1).text());
                IMDBFilmModel.setYear(Integer.parseInt(row.getElementsByClass("lister-item-year text-muted unbold").text().replaceAll("[^\\d.]", "")));
                IMDBFilmModel.setRuntime(row.getElementsByClass("runtime").text());
                IMDBFilmModel.setGenre(row.getElementsByClass("genre").text());
                IMDBFilmModel.setImdbPoint(Double.parseDouble(row.getElementsByClass("ipl-rating-star__rating").get(0).text()));
                setDirectorAndStars(row.getElementsByClass("text-muted text-small").get(1).children().eachText(), IMDBFilmModel);
                IMDBFilmModel.setGross(getGrossFromElements(row.getElementsByClass("text-muted text-small").get(2).children()));
                IMDBFilmModel.setInformation(IMDB_URL + row.getElementsByClass("lister-item-header").get(0).child(1).attr("href"));
                filmModelList.add(IMDBFilmModel);
            }
        }
        IMDBFilmRepository.saveAll(filmModelList);
    }

    private void setDirectorAndStars(List<String> eachText, IMDBFilmModel IMDBFilmModel) {
        List<String> directorList = new ArrayList<>();
        List<String> starList = new ArrayList<>();
        boolean isStar = false;
        for (String name : eachText) {
            if (!isStar) {
                if (Objects.equals(name, "|")) {
                    isStar = true;
                } else {
                    directorList.add(name);
                }
            } else {
                starList.add(name);
            }
        }
        if (starList.isEmpty()) {
            starList.addAll(directorList);
            directorList.clear();
            directorList.add(" ");
        }
        IMDBFilmModel.setDirectors(getStringData(directorList).toString());
        IMDBFilmModel.setStars(getStringData(starList).toString());
    }

    private StringBuilder getStringData(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList.size() >= 2) {
            for (String name : stringList) {
                stringBuilder.append(name);
                stringBuilder.append(", ");
            }
        } else {
            stringBuilder.append(stringList.get(0));
        }
        return stringBuilder;
    }

    private String getGrossFromElements(Elements elements){
        if (elements.get(elements.size()-1).text().contains("$")){
            return elements.get(elements.size()-1).text();
        } else {
            return "Undefined";
        }
    }
}