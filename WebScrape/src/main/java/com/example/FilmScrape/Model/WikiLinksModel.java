package com.example.FilmScrape.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wikilinks")
@NoArgsConstructor
@AllArgsConstructor
public class WikiLinksModel {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long Id;

    @Column(name = "Link")
    private String link;

}
