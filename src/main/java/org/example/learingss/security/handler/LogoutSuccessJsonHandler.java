package org.example.learingss.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogoutSuccessJsonHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler
{
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"msg\":\"登出成功\"}");
    }
}
