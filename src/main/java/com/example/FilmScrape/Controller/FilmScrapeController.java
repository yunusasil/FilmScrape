package com.example.FilmScrape.Controller;

import com.example.FilmScrape.Model.IMDBFilmModel;
import com.example.FilmScrape.Model.WikiFilmModel;
import com.example.FilmScrape.Service.IMDBFilmService;
import com.example.FilmScrape.Service.WikiFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class FilmScrapeController {

    @Autowired
    private IMDBFilmService IMDBFilmService;

    @Autowired
    private WikiFilmService wikiFilmService;

    @Autowired
    public FilmScrapeController(IMDBFilmService IMDBFilmService) {
        this.IMDBFilmService = IMDBFilmService;
    }


    @GetMapping("")
    public String mainPage() throws IOException {
        return "index";
    }

    @GetMapping("/scrapeIMDBPage")
    public String scrapeIMDBPage(Model model){
        model.addAttribute("isScraped", !IMDBFilmService.getIMDBFilmAll().isEmpty());
        return "IMDBScrapeData";
    }

    @PostMapping("/scrapeIMDB")
    public String scrapeIMDB(Model model) throws IOException{
        IMDBFilmService.scrapeData();
        model.addAttribute("isScraped", true);
        return "IMDBScrapeData";
    }

    @GetMapping("/scrapeWikiPage")
    public String scrapeWikiPage(Model model){
        model.addAttribute("isScrapedWiki", !wikiFilmService.getAllWikiFilms().isEmpty());
        return "WikiScrapeData";
    }

    @PostMapping("/scrapeWiki")
    public String scrapeWiki(Model model) throws IOException{
        wikiFilmService.scrapeData();
        model.addAttribute("isScrapedWiki", true);
        return "WikiScrapeData";
    }

    @GetMapping("/IMDBFilmList")
    public String getAllIMDBFilms(Model model){
        List<IMDBFilmModel> filmModelList = IMDBFilmService.getIMDBFilmAll();
        model.addAttribute("imdbFilmList", filmModelList);
        return "IMDBFilmList";
    }

    @GetMapping("/WikiFilmList")
    public String getAllWikiFilms(Model model){
        List<WikiFilmModel> filmModelList = wikiFilmService.getAllWikiFilms();
        model.addAttribute("wikiFilmList", filmModelList);
        return "WikiFilmList";
    }

    @GetMapping("/film")
    public String getFilm(Model model){
        List<IMDBFilmModel> filmModelList = IMDBFilmService.getIMDBFilmAll();
        model.addAttribute("filmList", filmModelList);
        return "FilmList";
    }

    @GetMapping("/film/{id}")
    public String getFilmByWikiAndImdb(Model model, @PathVariable Long id){
        IMDBFilmModel imdbFilmModel = IMDBFilmService.getIMDBFilmById(id);
        WikiFilmModel wikiFilmModel = wikiFilmService.getWikiFilmById(id);
        model.addAttribute("wikiFilm", wikiFilmModel);
        model.addAttribute("imdbFilm", imdbFilmModel);
        return "Film";
    }

    @GetMapping("/film/total")
    public String getTotalAnalyse(Model model){
        int sameName = 0;
        int sameRuntime = 0;
        int sameDirectors = 0;
        int sameYear = 0;
        List<IMDBFilmModel> imdbFilmModelList = IMDBFilmService.getIMDBFilmAll();
        List<WikiFilmModel> wikiFilmModelList = wikiFilmService.getAllWikiFilms();
        for (int i = 0; i < 1000; i++){
            IMDBFilmModel imdbFilmModel = imdbFilmModelList.get(i);
            WikiFilmModel wikiFilmModel = wikiFilmModelList.get(i);
            if (Objects.equals(imdbFilmModel.getName(), wikiFilmModel.getName())){
                sameName++;
            }
            if (Objects.nonNull(wikiFilmModel.getRuntime())){
                if (wikiFilmModel.getRuntime().contains(imdbFilmModel.getRuntime())){
                    sameRuntime++;
                }
            }
            if (Objects.nonNull(wikiFilmModel.getDirectors())){
                String imdbDirectors = imdbFilmModel.getDirectors();
                if (imdbDirectors.contains(",")){
                    imdbDirectors.replaceAll(",","");
                }
                if (wikiFilmModel.getDirectors().contains(imdbDirectors)){
                    sameDirectors++;
                }
            }
            if (Objects.nonNull(wikiFilmModel.getYear())){
                if (wikiFilmModel.getYear().contains(imdbFilmModel.getYear().toString())){
                    sameYear++;
                }
            }
        }
        Map<String, Integer> analysisMap = new HashMap<>();
        analysisMap.put("sameName", sameName);
        analysisMap.put("sameRuntime", sameRuntime);
        analysisMap.put("sameDirectors", sameDirectors);
        analysisMap.put("sameYear", sameYear);
        model.addAttribute("analysisMap", analysisMap);
        System.out.println(sameName + " " + sameRuntime + " " + sameDirectors + " " + sameYear);
        return "TotalAnalyse";
    }
}
