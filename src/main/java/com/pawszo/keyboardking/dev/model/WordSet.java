package com.pawszo.keyboardking.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WordSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "words", joinColumns = @JoinColumn(name = "wordSet_id"))
    @Column(name = "word")
    private List<String> words = new ArrayList<>();
    private Integer difficulty;
    private String language;
}
