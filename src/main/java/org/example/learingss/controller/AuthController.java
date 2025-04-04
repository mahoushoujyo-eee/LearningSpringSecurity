package org.example.learingss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @RequestMapping("/hello")
    public String hello()
    {
        return "{\"message\":\"Hello! You passed our check!\"}";
    }
}
