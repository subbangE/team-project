package com.myapp.team.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    void updateUserRole(@Param("userId") String userId, @Param("role") String role);
}
