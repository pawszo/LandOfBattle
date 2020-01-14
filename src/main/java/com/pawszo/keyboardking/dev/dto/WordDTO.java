package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordDTO {
    private Long id;
    private String name;
    private String language;
    private Integer difficulty;
}
