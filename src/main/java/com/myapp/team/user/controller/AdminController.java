package com.myapp.team.user.controller;

import com.myapp.team.Board.Question.Question;
import com.myapp.team.product.Product;
import com.myapp.team.user.model.User;
import com.myapp.team.user.service.AdminService;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/grant")
    @ResponseBody
    public String grantAdmin(@RequestParam String userId, String role) {
        adminService.updateUserRole(userId, "ADMIN");
        adminService.updateUserRole(userId, "USER");
        return userId + "의 권한이 " + role + "(으)로 변경되었습니다";
    }

    //ADMIN 역할을 가진 유저만 페이지에 입장가능하게 해줌
    @GetMapping

    public String adminPage() {
        return "admin";
    }

    @GetMapping("/userlist")
    public String userListPage(Model model) {
        List<User> userList = adminService.findAllUser();
        model.addAttribute("users", userList);
        return "userlist";
    }

    @GetMapping("/adminlist")
    public String adminListPage(Model model) {
        List<User> userList = adminService.findAllUser();
        model.addAttribute("users", userList);
        return "adminlist";
    }

    @GetMapping("/prodlist")
    public String prodListPage(Model model) {
        List<Product> prodList = adminService.findAllProduct();
        model.addAttribute("prods", prodList);
        return "prodlist";
    }

    @GetMapping("/questionlist")
    public String QuestionListPage(Model model) {
        List<Question> questionList = adminService.findAllQuestion();
        model.addAttribute("questions", questionList);
        return "questionlist";
    }
}
