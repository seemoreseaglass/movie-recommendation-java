package com.seemoreseaglass.controller;

import com.seemoreseaglass.entity.Title;
import com.seemoreseaglass.service.UserService;
import com.seemoreseaglass.repository.TitleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private TitleRepository titleRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        try {
            userService.register(username, password);
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("username", username);
            return "register";
        }
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Title> results = titleRepository.findByPrimaryTitleContainingIgnoreCase(q);
        model.addAttribute("results", results);
        return "search";
    }
}
