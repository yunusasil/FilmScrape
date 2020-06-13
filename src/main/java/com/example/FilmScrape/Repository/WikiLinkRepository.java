package com.example.FilmScrape.Repository;

import com.example.FilmScrape.Model.WikiLinksModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiLinkRepository extends JpaRepository<WikiLinksModel, Long> {
}
