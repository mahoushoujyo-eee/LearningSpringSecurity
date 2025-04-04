package org.example.learingss.security;

import lombok.extern.slf4j.Slf4j;
import org.example.learingss.entities.User;
import org.example.learingss.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService, UserDetailsPasswordService
{
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword)
    {
        log.info("update password");
        log.info("new password: {}", newPassword);
        log.info("user: {}", user);
        User user1 = (User) user;
        user1.setPassword(newPassword);
        userMapper.resetPasswordByUsername((User) user);
        return user1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        log.info("load user by username");
        log.info("username: {}", username);
        if (userMapper.ifContainsUsername(username))
        {
            User user = userMapper.getUserByUsername(username);
            return user;
        }
        return null;
    }
}
