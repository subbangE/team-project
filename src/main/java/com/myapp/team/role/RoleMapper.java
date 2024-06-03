package com.myapp.team.role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    @Select("select * from user where id=#{id}")
    Role getRole(@Param("id") String id);
}
