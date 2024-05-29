package com.myapp.team.user.service;

import com.myapp.team.user.mapper.UserMapper;
import com.myapp.team.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findByUserId(userId);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException(userId + "에 해당하는 사용자를 찾을 수 없습니다.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPw(),
                new ArrayList<>()
                );
    }
}
