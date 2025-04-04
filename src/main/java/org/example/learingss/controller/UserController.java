package org.example.learingss.controller;

import org.example.learingss.entities.User;
import org.example.learingss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(@RequestBody User user)
    {
        return userService.register(user);
    }
}
