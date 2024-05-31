package com.myapp.team.user.service;

import com.myapp.team.user.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public void updateUserRole(String userId, String role) {
        adminMapper.updateUserRole(userId, role);
    }
}
