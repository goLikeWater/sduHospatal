package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
    @Select("select * from user where userId = #{id}")
    User findById(Integer id);

    @Insert("insert into user (username,password) values(#{username},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void add(User user);
    @Delete("delete from user where userId = #{id}")
    void delete(Integer id);
    @Update("update user set email = #{email} where userId = #{userId}")
    void update(User user);
    @Update("update user set pic = #{avatarUrl} where userId = #{id}")
    void updateAvatar(String avatarUrl,Integer id);
    @Update("update user set password = #{newPwd} where userId = #{id}")
    void updatePwd(String newPwd,Integer id);

}
