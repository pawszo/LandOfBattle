package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import com.pawszo.keyboardking.dev.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @GetMapping("/highscores")
    public String getHighcores(Model model) {
        List<ScoreDTO> scoreList = scoreService.getScoreList();
        model.addAttribute("scoreList", scoreList);
        return "highscores";
    }

}

