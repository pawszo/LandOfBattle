package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.ICreateScoreDTO;
import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import com.pawszo.keyboardking.dev.model.Score;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScoreMapper {

    public Score createModel(ICreateScoreDTO iCreateScoreDTO) {
        Score score = new Score();
        score.setStats(iCreateScoreDTO.getStats());
        score.setNickname(iCreateScoreDTO.getNickname());
        score.setGame(iCreateScoreDTO.getGame());
        return score;
    }

    public ScoreDTO mapToDTO(Score score) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setId(score.getId());
        scoreDTO.setStats(score.getStats());
        scoreDTO.setNickname(score.getNickname());
        scoreDTO.setGame(score.getGame());
        return scoreDTO;
    }

    public Score mapToModel(ScoreDTO scoreDTO) {
        Score score = new Score();
        score.setId(scoreDTO.getId());
        score.setNickname(scoreDTO.getNickname());
        score.setStats(scoreDTO.getStats());
        score.setGame(scoreDTO.getGame());
        return score;
    }

    public Map<String, String> mapToMap(ScoreDTO scoreDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", scoreDTO.getNickname());
        map.put("points", scoreDTO.getStats().get("points"));
        map.put("time", scoreDTO.getStats().get("time"));
        return map;
    }
}
