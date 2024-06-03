package com.myapp.team.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoleController {
    //roleMapper 선언하고 생성자 주입

    @GetMapping("/users/{ id }")
    public Role getRole(@PathVariable("id") String id) {
        Role role = RoleMapper.getRole(id);
        return role;


    }
}