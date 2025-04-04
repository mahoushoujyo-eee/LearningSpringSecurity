package org.example.learingss.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learingss.entities.User;
import org.example.learingss.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        log.info("用户名：{}", user.getUsername());
        if (userMapper.ifContainsUsername(user.getUsername()))
        {
            return "用户名已存在";
        }
        else
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insertUser(user);
            return "注册成功";
        }
    }
}
