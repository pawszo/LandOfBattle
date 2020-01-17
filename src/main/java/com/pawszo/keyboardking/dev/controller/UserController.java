package com.pawszo.keyboardking.dev.controller;

import com.pawszo.keyboardking.dev.dto.*;
import com.pawszo.keyboardking.dev.mapper.RoleMapper;
import com.pawszo.keyboardking.dev.mapper.ScoreMapper;
import com.pawszo.keyboardking.dev.mapper.UserMapper;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import com.pawszo.keyboardking.dev.repository.ScoreRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import com.pawszo.keyboardking.dev.service.ScoreService;
import com.pawszo.keyboardking.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Transactional
    @GetMapping("/admin/users")
    public String getUsersList(Model model) {
        model.addAttribute("usersList", userRepository.findAll().stream().map(u -> userMapper.mapToDTO(u)).collect(Collectors.toList()));
        return "users";
    }

    @Transactional
    @GetMapping("admin/roles")
    public String getRolesList(Model model) {
        model.addAttribute("rolesList", roleRepository.findAll().stream().map(r -> roleMapper.mapToDTO(r)).collect(Collectors.toList()));
        return "roles";
    }

    @Transactional
    @GetMapping(value = {"/register/{scoreId}", "/register"})
    public ModelAndView getRegisterForm(
            @PathVariable(required = false) Long scoreId, Model model) {

        ModelAndView mv = new ModelAndView("register");
        CreateUserDTO userDTO = new CreateUserDTO();

        if (scoreId != null) {
            ScoreDTO scoreDTO = scoreMapper.mapToDTO(scoreRepository.getOne(scoreId));
            userDTO.setNickname(scoreDTO.getNickname());
            mv.addObject("hasScore", true);
        } else {
            mv.addObject("hasScore", false);
        }
        mv.addObject("userDTO", userDTO);


        return mv;
    }
 /*   @PostMapping("/register/{scoreID}")
    public ModelAndView registerUserWithScore(@ModelAttribute("userDTO") @Valid CreateUserDTO userDTO, @PathVariable Long scoreID) {
        userService.registerUser(userDTO);
        //
    }
    */

    @GetMapping("/changepassword")
    public String lostPasswordPage(Model model, HttpServletRequest request) {
        String username = "";
        PasswordLostDTO dto = new PasswordLostDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        model.addAttribute("username", username);
        if (!username.isEmpty()) {
            dto.setEmail(userRepository.findByNickname(username).get().getEmail());
        }
        model.addAttribute("passwordLostDTO", dto);
        return "changepassword";
    }

    @GetMapping("/changepassword/{userId}")
    public String setNewPassword(Model model, @PathVariable Long userId) {
        User user = userRepository.getOne(userId);
        if (user == null || !user.getState().equalsIgnoreCase("passwordChange")) {

            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Link invalid. Contact website administrator or generate new password change link");
            return "home";
        } else {
            PasswordUpdateUserDTO dto = new PasswordUpdateUserDTO();
            dto.setId(user.getId());
            dto.setNickname(user.getNickname());
            dto.setPreviousPassword(user.getPassword());
            model.addAttribute("user", dto);
            return "newPassword";
        }
    }

    @PostMapping("/updatepassword")
    public String updatePassword(@ModelAttribute PasswordUpdateUserDTO passwordUpdateUserDTO, HttpServletRequest request, HttpServletResponse response) {
        userService.updatePassword(passwordUpdateUserDTO);
        RequestDispatcher rd = request.getRequestDispatcher("/logout");
        try {
            rd.forward(request, response);
        } catch (IOException | ServletException e) {
            System.out.println(e);
        }
        return "home";

    }

    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute("userDTO") @Valid CreateUserDTO userDTO,
                                     BindingResult result, ModelMap model,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        UserDTO user = userService.registerUser(userDTO);

        if (result.hasErrors()) {
            return new RedirectView("registerFailed");
        }

        redirectAttributes.addAttribute("messageType", "success");
        redirectAttributes.addAttribute("message", "User successfully registered.");

        return new RedirectView("/home");
    }

    @GetMapping("/signin")
    public String getLoginPage(@RequestParam(required = false, name = "error") Boolean error, Model model) {
        if (error != null && error == true) {
            model.addAttribute("error", "There is an error. Please try again.");
        }
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }


}
