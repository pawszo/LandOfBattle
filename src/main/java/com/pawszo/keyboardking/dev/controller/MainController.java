package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.dto.UserDTO;
import com.pawszo.keyboardking.dev.service.ScoreService;
import com.pawszo.keyboardking.dev.service.UserService;
import com.pawszo.keyboardking.dev.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private WordService wordService;

    /*   @PostConstruct
       public void addUsers() {
           CreateUserDTO user1 = new CreateUserDTO("dupadupa123", "dupa@dupa.pl", "password1", "100", "100");
           CreateUserDTO user2 = new CreateUserDTO("kupakupa321", "kupa@kupa.pl", "password1", "101", "101");
           CreateUserDTO user3 = new CreateUserDTO("siusiu555", "siu@siu.pl", "password1", "102", "102");
           userService.registerUser(user1);
           userService.registerUser(user2);
           userService.registerUser(user3);

       }
   */
    @GetMapping(value = {"/home", "/"})
    public String getHomepage(Model model, @ModelAttribute("user") UserDTO user, @RequestParam(required = false) String messageType,
                              @RequestParam(required = false) String message) {

        model.addAttribute("messageType", messageType);
        model.addAttribute("message", message);
        //model.addAttribute("scoreList", scoreService.getScoreList());
        //model.addAttribute("userList", userService.getUsersList());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
        } else {
            String username = principal.toString();
        }
        return "home";
    }

    @GetMapping(value = {"/terms"})
    public String getPolicyAndTerms() {
        return "terms";
    }

    @GetMapping(value = {"/author", "/about"})
    public String getAboutMe() {
        return "author";
    }


}
