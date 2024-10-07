package com.example.demo.service;

import com.example.demo.pojo.User;

public interface UserService{
    User findByUsername(String username);
    User findById(Integer id);

    void register(User u);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd,Integer id);

    void delete(Integer userId);
}
