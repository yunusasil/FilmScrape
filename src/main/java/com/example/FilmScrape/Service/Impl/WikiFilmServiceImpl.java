package com.example.FilmScrape.Service.Impl;

import com.example.FilmScrape.Model.WikiFilmModel;
import com.example.FilmScrape.Model.WikiLinksModel;
import com.example.FilmScrape.Repository.WikiFilmRepository;
import com.example.FilmScrape.Repository.WikiLinkRepository;
import com.example.FilmScrape.Service.WikiFilmService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WikiFilmServiceImpl implements WikiFilmService {

    @Autowired
    private WikiLinkRepository wikiLinkRepository;

    @Autowired
    private WikiFilmRepository wikiFilmRepository;

    @Override
    public List<WikiFilmModel> getAllWikiFilms(){
        return wikiFilmRepository.findAll();
    }

    @Override
    public WikiFilmModel getWikiFilmById(Long id){
        Optional<WikiFilmModel> optFilm = wikiFilmRepository.findById(id);
        return optFilm.get();
    }

    @Override
    public void saveWikiFilm(WikiFilmModel wikiFilmModel){
        wikiFilmRepository.save(wikiFilmModel);
    }

    @Override
    public void deleteWikiFilm(WikiFilmModel wikiFilmModel){
        wikiFilmRepository.delete(wikiFilmModel);
    }

    @Override
    public void scrapeData() throws IOException{
        List<WikiLinksModel> wikiLinks = wikiLinkRepository.findAll();
        List<WikiFilmModel> wikiFilmModelList = new ArrayList<>();
        for (WikiLinksModel wikiLinksModel : wikiLinks){
            Document document = Jsoup.connect(wikiLinksModel.getLink())
                    .timeout(70000)
                    .maxBodySize(1024 * 1024 * 10)
                    .get();
            WikiFilmModel wikiFilmModel = new WikiFilmModel();
            for (Element infoBox : document.getElementsByClass("infobox vevent")){
                wikiFilmModel.setName(infoBox.children().get(0).children().get(0).text());
                for (Element elementFilm : infoBox.children().get(0).children()){
                    if (elementFilm.text().contains("Direct")){
                        wikiFilmModel.setDirectors(elementFilm.children().get(1).text());
                    } else if (elementFilm.text().contains("Release date")){
                        wikiFilmModel.setYear(elementFilm.children().get(1).text());
                    } else if (elementFilm.text().contains("Running time")){
                        wikiFilmModel.setRuntime(elementFilm.children().get(1).text());
                    } else if (elementFilm.text().contains("Starring")){
                        wikiFilmModel.setStars(elementFilm.children().get(1).text());
                    } else if (elementFilm.text().contains("Box office")) {
                        wikiFilmModel.setGross(elementFilm.children().get(1).text());
                    }
                }
            }
            wikiFilmModel.setInformation(wikiLinksModel.getLink());
            wikiFilmModelList.add(wikiFilmModel);
        }
        wikiFilmRepository.saveAll(wikiFilmModelList);
    }
}
