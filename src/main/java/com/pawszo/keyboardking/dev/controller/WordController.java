package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.dto.CreateWordSetDTO;
import com.pawszo.keyboardking.dev.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin/words")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping
    public String getWordList(Model model) {
        model.addAttribute("wordList", wordService.getAllWords());
        model.addAttribute("wordSetDTO", new CreateWordSetDTO());
        return "words";
    }

    @PostMapping
    public RedirectView addWordSet(@ModelAttribute CreateWordSetDTO createWordSetDTO) {
        wordService.addWords(createWordSetDTO);
        return new RedirectView("/admin/words");
    }

    @PostMapping("/{id}")
    public RedirectView deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return new RedirectView("/admin/words");
    }


}
