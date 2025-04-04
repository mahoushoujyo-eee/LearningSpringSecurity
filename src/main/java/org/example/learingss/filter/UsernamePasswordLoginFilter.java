package org.example.learingss.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Slf4j
public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter
{
    @SneakyThrows
    @Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {

    // 1. 确保请求方法为 POST 且 Content-Type 是 application/json
    if (!"POST".equals(request.getMethod())) {
        throw new AuthenticationServiceException("仅支持 POST 请求");
    }
    if (!"application/json".equals(request.getContentType())) {
        throw new AuthenticationServiceException("请求头 Content-Type 必须为 application/json");
    }

    // 2. 读取并解析 JSON 请求体
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});

    String username = credentials.get("username");
    String password = credentials.get("password");

    // 3. 创建认证令牌
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username,password);

    // 4. 记录日志（使用 Lombok 的 log）
    log.info("接收到登录请求: username={}, password={}", username, password);

    // 5. 调用父类方法继续认证流程
    return this.getAuthenticationManager().authenticate(authenticationToken);
}

}
