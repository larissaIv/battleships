package com.softuni.battleships.controllers;

import com.softuni.battleships.DTO.LoginDTO;
import com.softuni.battleships.DTO.UserRegistrationDTO;
import com.softuni.battleships.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO initRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.userService.register(registrationDTO)) {
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDTO", bindingResult);

            return "redirect: /register";
        }

        return "redirect: /login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDTO loginDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);

            return "redirect: /login";
        }

        return "redirect: /home";

    }
}
