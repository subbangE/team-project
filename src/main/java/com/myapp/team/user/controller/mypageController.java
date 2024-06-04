package com.myapp.team.user.controller;

import com.myapp.team.user.model.User;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
@PreAuthorize("#userNo == authentication.principal.userNo")

public class mypageController {

    @Autowired
    UserService userService;

    @GetMapping("/{userNo}")
    public String mypage(@PathVariable("userNo") int userNo, Model model) {
        User user = userService.getUserByUserNo(userNo);
        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user, Model model) {
        System.out.println("유저번호: "+ user.getUserNo());
        userService.updateUser(user);
        User updateUser = userService.getUserByUserNo(user.getUserNo());
        model.addAttribute("user", updateUser);
        return "redirect:/mypage/" + user.getUserNo();
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) {
        userService.deleteUserByUserNo(userId);
        return "deleteSuccess";
    }

}
