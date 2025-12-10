package org.example.springinit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT令牌生成与解析工具类
 */
@Component
public class JwtUtil {
    /**
     * JWT密钥（建议配置在application.yml，长度至少32位）
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 令牌过期时间（单位：秒，此处配置2小时）
     */
    @Value("${jwt.expire:7200}")
    private Long expire;

    /**
     * 生成JWT令牌
     * @param userId 用户ID
     * @param role 用户角色
     * @return JWT令牌
     */
    public String generateToken(Long userId, Integer role) {
        // 1. 构建密钥
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        // 2. 设置令牌过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expire * 1000);

        // 3. 构建自定义Claims（存储用户核心信息）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        // 4. 生成令牌
        return Jwts.builder()
                .setClaims(claims) // 自定义载荷
                .setSubject(userId.toString()) // 主题（用户ID）
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(expireDate) // 过期时间
                .signWith(key) // 签名
                .compact();
    }

    /**
     * 解析令牌，获取Claims
     * @param token JWT令牌
     * @return 载荷信息
     */
    public Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取令牌过期时间（秒）
     */
    public Long getExpireTime() {
        return expire;
    }
    /**
     * 从令牌解析用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从令牌解析用户角色
     * @param token JWT令牌
     * @return 用户角色（1-个人，2-商户）
     */
    public Integer getRoleFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", Integer.class);
    }
    // 生成密钥
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    /**
     * 生成管理员JWT令牌
     */
    public String generateAdminToken(Long userId, String username, Integer role) {
        return Jwts.builder()
                // 载荷：用户ID
                .claim("userId", userId)
                // 载荷：用户名
                .claim("username", username)
                // 载荷：角色（3-管理员）
                .claim("role", role)
                // 签发时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                // 签名
                .signWith(getSecretKey())
                .compact();
    }
}