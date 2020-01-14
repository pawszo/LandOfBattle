package com.pawszo.keyboardking.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(
        name = "word",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"name", "language"})
)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    private String language;
    private Integer difficulty;


}
