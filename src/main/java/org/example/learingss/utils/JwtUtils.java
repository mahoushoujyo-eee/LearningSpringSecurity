package org.example.learingss.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.learingss.entities.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtUtils {

    // 密钥（需严格保密）
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("star_vault??????.......????????????????????".getBytes(StandardCharsets.UTF_8));

    // 过期时间（示例：2小时）
    private static final long EXPIRE = TimeUnit.HOURS.toMillis(2);

    /**
     * 生成JWT Token
     * @param user 用户信息
     * @return JWT字符串
     */
    public static String generateToken(User user)
    {
        // Header信息
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        // Payload负载
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        payload.put("username", user.getUsername());
        payload.put("exp", new Date(System.currentTimeMillis() + EXPIRE)); // 过期时间
        payload.put("iat", new Date()); // 签发时间
        payload.put("iss", "StarVault"); // 签发者

        // 生成Token
        return Jwts.builder()
                .setHeaderParams(header)
                .setClaims(payload)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 使用SecretKey和算法
                .compact();
    }

    public static boolean validateToken(String token)
    {
        try
        {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static User parseToken(String token)
    {
        try
        {
            Map<String, Object> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            User user = new User();
            user.setId((Integer) claims.get("userId"));
            user.setUsername((String) claims.get("username"));

            return user;
       }
        catch (Exception e)
        {
            log.error("解析Token失败：{}", e.getMessage());
            return null;
        }
    }



}

