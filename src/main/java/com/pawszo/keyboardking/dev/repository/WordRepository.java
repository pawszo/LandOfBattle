package com.pawszo.keyboardking.dev.repository;

import com.pawszo.keyboardking.dev.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByDifficultyAndLanguage(Integer difficulty, String language);

    List<Word> findAllByLanguage(String language);


}
