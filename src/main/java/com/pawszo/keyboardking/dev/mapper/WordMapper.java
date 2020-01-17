package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.WordDTO;
import com.pawszo.keyboardking.dev.model.Word;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    public Word createModel(String wordString, String language) {
        Word word = new Word();
        word.setName(wordString);
        word.setLanguage(language);
        Integer difficulty = wordString.length() - 2;
        if (difficulty > 10) difficulty = 10;
        word.setDifficulty(difficulty);
        return word;
    }

    public WordDTO mapToDTO(Word word) {
        return new WordDTO(word.getId(), word.getName(), word.getLanguage(), word.getDifficulty());
    }

    public Word mapToModel(WordDTO wordDTO) {
        return new Word(wordDTO.getId(), wordDTO.getName(), wordDTO.getLanguage(), wordDTO.getDifficulty());
    }


}
