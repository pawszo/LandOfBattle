package com.pawszo.keyboardking.dev.repository;

import com.pawszo.keyboardking.dev.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByNickname(String nickname);

    Boolean existsByNickname(String nickname);

    List<Score> findAllByGame(String game);
}
