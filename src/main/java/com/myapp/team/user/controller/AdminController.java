package com.myapp.team.user.controller;

import com.myapp.team.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/grant")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public String grantAdmin(@RequestParam String userId, String role) {
        adminService.updateUserRole(userId, "ADMIN");
        adminService.updateUserRole(userId, "USER");
        return userId + "의 권한이 " + role + "(으)로 변경되었습니다";
    }

    //ADMIN 역할을 가진 유저만 페이지에 입장가능하게 해줌
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }
}
