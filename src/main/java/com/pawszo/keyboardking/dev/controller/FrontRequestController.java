package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.comparator.ScorePointsComparator;
import com.pawszo.keyboardking.dev.comparator.ScoreTimeComparator;
import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import com.pawszo.keyboardking.dev.dto.TypeOShooterScoreDTO;
import com.pawszo.keyboardking.dev.mapper.ScoreMapper;
import com.pawszo.keyboardking.dev.service.ScoreService;
import com.pawszo.keyboardking.dev.service.UserService;
import com.pawszo.keyboardking.dev.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FrontRequestController {

    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private WordService wordService;
    @Autowired
    private ScoreTimeComparator scoreTimeComparator;
    @Autowired
    private ScorePointsComparator scorePointsComparator;
    @Autowired
    private ScoreMapper scoreMapper;

    @PostMapping("/scores")
    public Long addScore(@RequestBody Map<String, String> results, Model model) {
        ScoreDTO scoreDTO = scoreService.addScore(new TypeOShooterScoreDTO(
                results.get("time"),
                results.get("points"),
                results.get("killedBy"),
                results.get("nickname"),
                results.get("game")
        ));
        model.addAttribute("scoreDTO", scoreDTO);
        System.out.println(scoreDTO);
        return scoreDTO.getId();
    }

    @PostMapping("/phrase")
    public String isUserDetailUnique(@RequestBody Map<String, String> params) {
        return userService.isUserDetailUnique((params.get("phrase")), params.get("type")).toString();
    }

    @PostMapping("/words")
    public List<String> getRandom20WordStrings(@RequestBody Map<String, String> params) {
        Integer level = Integer.parseInt(params.get("level"));
        String language = params.get("language");
        return wordService.getRandom10WordStrings(level, language);
    }

    @PostMapping("/highscores/{game}")
    public List<Map<String, String>> getAllScoresByGame(@PathVariable String game, @RequestBody Map<String, String> params) {

        List<ScoreDTO> scoreList = scoreService.getAllScoresByGame(game);
        if (params.containsKey("sort") && params.get("sort").equalsIgnoreCase("points")) {
            Collections.sort(scoreList, scorePointsComparator);
        } else if (params.containsKey("sort") && params.get("sort").equalsIgnoreCase("time")) {
            Collections.sort(scoreList, scoreTimeComparator);
        }

        if (params.containsKey("amount")) {
            int limit = Integer.parseInt(params.get("amount"));
            scoreList = scoreList.stream().limit(limit).collect(Collectors.toList());
        }

        return scoreList.stream().map(s -> scoreMapper.mapToMap(s)).collect(Collectors.toList());
    }
}
