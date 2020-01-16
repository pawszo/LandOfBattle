package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.dto.CastleDefenceScoreDTO;
import com.pawszo.keyboardking.dev.dto.TypeOShooterScoreDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
@CrossOrigin
public class GameController {

    @GetMapping(value = {"/shooter", "/starter"})
    public String getTypeOShooterGame(Model model) {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        model.addAttribute("username", username);
        TypeOShooterScoreDTO scoreDTO = new TypeOShooterScoreDTO();
        scoreDTO.setGame("Type'o'Shooter");
        model.addAttribute("createScoreDTO", scoreDTO);

        return "shooter";
    }

    @GetMapping(value = {"/castle", "/defence", "/castleDefence"})
    public String getCastleDefenceGame(Model model) {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        model.addAttribute("username", username);
        CastleDefenceScoreDTO scoreDTO = new CastleDefenceScoreDTO();
        scoreDTO.setGame("Castle-defence");
        model.addAttribute("createScoreDTO", scoreDTO);
        return "defence";
    }
}
