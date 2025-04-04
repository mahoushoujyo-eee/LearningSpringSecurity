package org.example.learingss.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.learingss.entities.User;
import org.example.learingss.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class TokenFilter extends OncePerRequestFilter
{
    public TokenFilter() {}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String token = request.getHeader("token");
        if (token == null || token.isEmpty())
        {
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().write("{\"msg\":\"token为空\"}");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (JwtUtils.validateToken(token))
        {
            User user = JwtUtils.parseToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else
        {
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().write("{\"msg\":\"token无效\"}");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);

    }
}
