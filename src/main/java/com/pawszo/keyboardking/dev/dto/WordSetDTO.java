package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordSetDTO {

    private Long id;
    private List<String> words = new ArrayList<>();
    private Integer difficulty;
    private String language;
}
