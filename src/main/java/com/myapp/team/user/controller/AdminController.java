package com.myapp.team.user.controller;

import com.myapp.team.user.service.AdminService;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin") //태형(경로설정)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;






    @PostMapping("/admin/grant")
    @ResponseBody
    public String grantAdmin(@RequestParam String userId, String role) {
        adminService.updateUserRole(userId, "ADMIN");
        adminService.updateUserRole(userId, "USER");
        return userId + "의 권한이 " + role + "(으)로 변경되었습니다";
    }

    //ADMIN 역할을 가진 유저만 페이지에 입장가능하게 해줌
    @GetMapping("/admin")

    public String adminPage() {
        return "admin";
    }
}
