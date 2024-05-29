package com.myapp.team.user.mapper;

import com.myapp.team.user.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    void insertUser(User user);

    User findByUserId(String userId);

}
