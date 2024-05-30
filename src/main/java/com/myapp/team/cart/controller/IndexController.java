package com.myapp.team.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home(Model model) {
        // 필요한 데이터 모델에 추가
        return "index";
    }
}
