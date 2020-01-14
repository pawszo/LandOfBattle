package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.CreateWordSetDTO;
import com.pawszo.keyboardking.dev.dto.WordSetDTO;
import com.pawszo.keyboardking.dev.model.WordSet;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordSetMapper {

    public WordSet createModel(CreateWordSetDTO dto) {
        WordSet wordSet = new WordSet();
        wordSet.setLanguage(dto.getLanguage());
        wordSet.setWords(Arrays.asList(dto.getWords().split(" ")));
        return wordSet;
    }

    public WordSetDTO mapToDTO(WordSet wordSet) {
        WordSetDTO dto = new WordSetDTO();
        dto.setDifficulty(wordSet.getDifficulty());
        dto.setId(wordSet.getId());
        dto.setLanguage(wordSet.getLanguage());
        dto.setWords(wordSet.getWords());
        return dto;
    }

    public WordSet mapToModel(WordSetDTO dto) {
        return new WordSet(dto.getId(), dto.getWords(), dto.getDifficulty(), dto.getLanguage());
    }
}
