package org.example.learingss.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.learingss.entities.User;
import org.example.learingss.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "登录成功");
        map.put("token", JwtUtils.generateToken((User) authentication.getPrincipal()));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
