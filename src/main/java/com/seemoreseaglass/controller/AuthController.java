package com.seemoreseaglass.controller;

import com.seemoreseaglass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

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
            return "redirect:/login"; // 登録成功時はログインページへ
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage()); // エラーメッセージを渡す
            model.addAttribute("username", username);     // 入力内容をフォームに保持
            return "register";                            // 登録ページを再表示
        }
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "home";
    }
}
