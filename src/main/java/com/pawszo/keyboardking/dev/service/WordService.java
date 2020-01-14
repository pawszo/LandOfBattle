package com.pawszo.keyboardking.dev.service;

import com.pawszo.keyboardking.dev.dto.CreateWordSetDTO;
import com.pawszo.keyboardking.dev.dto.WordDTO;
import com.pawszo.keyboardking.dev.mapper.WordMapper;
import com.pawszo.keyboardking.dev.model.Word;
import com.pawszo.keyboardking.dev.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WordService {

    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private WordRepository wordRepository;

    public List<String> getRandom10WordStrings(Integer difficulty, String language) {
        List<String> wordList = wordRepository.findAllByDifficultyAndLanguage(difficulty, language).stream().map(w -> w.getName()).collect(Collectors.toList());
        Collections.shuffle(wordList);
        if (wordList.size() < 10) {
            return wordList;
        } else return wordList.subList(0, 9);
    }

    public List<WordDTO> addWords(CreateWordSetDTO dto) {
        ArrayList<String> existingWords = new ArrayList<>(wordRepository.findAllByLanguage(dto.getLanguage()).stream().map(w -> w.getName()).collect(Collectors.toList()));
        String language = dto.getLanguage();
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(dto.getWords()
                .replaceAll("\\W", " ")
                .replaceAll("\\d", " ")
                .toLowerCase().split("\\s+")));
        wordList.removeAll(existingWords);
        Set<Word> wordSet = wordList
                .stream()
                .filter(w -> (!w.equals(" ")))
                .filter(w -> w.length() > 1)
                .map(w -> wordMapper.createModel(w, language))
                .collect(Collectors.toSet());
        for (Word word : wordSet) {
            try {
                wordRepository.save(word);
            } catch (PersistenceException e) {
                System.out.println("Word \"" + word + "\" already exists.");
            }
        }
        wordRepository.saveAll(wordSet);
        return wordSet.stream().map(w -> wordMapper.mapToDTO(w)).collect(Collectors.toList());
    }

    public List<WordDTO> getAllWords() {
        return wordRepository.findAll().stream().map(w -> wordMapper.mapToDTO(w)).collect(Collectors.toList());
    }

    public List<WordDTO> getAllWordsByLanguage(String language) {
        return wordRepository.findAllByLanguage(language).stream().map(w -> wordMapper.mapToDTO(w)).collect(Collectors.toList());
    }

    public WordDTO deleteWord(Long id) {
        WordDTO dto = wordMapper.mapToDTO(wordRepository.getOne(id));
        wordRepository.deleteById(id);
        return dto;
    }
}
