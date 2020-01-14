package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.config.EmailSender;
import com.pawszo.keyboardking.dev.dto.PasswordLostDTO;
import com.pawszo.keyboardking.dev.mapper.UserMapper;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmailController {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    public EmailController(EmailSender emailSender,
                           TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/changepassword")
    public String sendPasswordChangeLink(@ModelAttribute PasswordLostDTO passwordLostDTO, Model model, HttpServletRequest request) {

        String email = passwordLostDTO.getEmail();
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).get();
            user.setState("passwordChange");
            userMapper.mapToDTO(userRepository.save(user));
            String description = "You received this email because a password change link was requested for your account. Click the link below to proceed." +
                    "If you haven't requested the link, please contact the admin (admin@landofbattle.pl).";
            Context context = new Context();
            context.setVariable("userID", user.getId());
            context.setVariable("header", "Change password on LandOfBattle.pl");
            context.setVariable("title", "Hello " + user.getNickname() + ",");
            context.setVariable("description", description);

            String body = templateEngine.process("mailPasswordChange", context);
            emailSender.sendEmail(email, "LandOfBattle - change password", body);
        }
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Password change link has been sent to your email address.");
        return "home";

    }


}
