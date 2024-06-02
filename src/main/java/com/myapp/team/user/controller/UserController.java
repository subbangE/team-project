package com.myapp.team.user.controller;

import com.myapp.team.user.model.User;
import com.myapp.team.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입 (회원가입페이지, DB입력)
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }
        userService.register(user);
        return "redirect:/register/result";
    }

    @GetMapping("/register/result")
    public String registerSuccess() {
        return "user/registerSuccess";
    }

    //로그인
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    //인덱스 페이지 호출(Get안해주면 페이지 안뜸)
    @GetMapping("/index")
    public String indexPage() {
        return "user/index";
    }

}

