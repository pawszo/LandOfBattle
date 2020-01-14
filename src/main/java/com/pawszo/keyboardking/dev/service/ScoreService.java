package com.pawszo.keyboardking.dev.service;

import com.pawszo.keyboardking.dev.dto.ICreateScoreDTO;
import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import com.pawszo.keyboardking.dev.dto.UserDTO;
import com.pawszo.keyboardking.dev.mapper.ScoreMapper;
import com.pawszo.keyboardking.dev.model.Score;
import com.pawszo.keyboardking.dev.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public ScoreDTO addScore(ICreateScoreDTO createScoreDTO) {
        System.out.println(createScoreDTO);
        Score score = scoreMapper.createModel(createScoreDTO);
        ScoreDTO scoreDTO = scoreMapper.mapToDTO(scoreRepository.save(score));
        scoreDTO.setNickname(createScoreDTO.getNickname());
        return scoreDTO;
    }

    @Transactional
    public ScoreDTO registerExistingScore(UserDTO userDTO, Long scoreID) {
        Score score = scoreRepository.getOne(scoreID);
        score.setNickname(userDTO.getNickname());
        return scoreMapper.mapToDTO(scoreRepository.save(score));
    }

    @Transactional
    public List<ScoreDTO> getScoreList() {

        return scoreRepository
                .findAll()
                .stream()
                .map(s -> scoreMapper.mapToDTO(s))
                .collect(Collectors.toList());
    }


    @Transactional
    public List<ScoreDTO> getAllScoresByGame(String game) {
        return scoreRepository
                .findAllByGame(game)
                .stream()
                .map(s -> scoreMapper.mapToDTO(s))
                .collect(Collectors.toList());
    }
}
