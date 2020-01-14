package com.pawszo.keyboardking.dev.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @ElementCollection // this is a collection of primitives
    @MapKeyColumn(name = "key") // column name for map "key"
    @Column(name = "value") // column name for map "value"
    private Map<String, String> stats;
    private String nickname;
    private String game;
}
