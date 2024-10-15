package com.base.common.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
@Slf4j
public class JwtTokenUtil {

    /**
     * token的头key
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * token 过期时间 30分钟
     */
    public static final long EXPIRATION = 1000 * 60 * 30;

    /**
     * 加密的key
     */
    public static final String APP_SECRET_KEY = "secret";

    /**
     * 权限的声明key
     */
    private static final String ROLE_CLAIMS = "role";

    /**
     * 生成token
     *
     * @param username 用户名
     * @param role 用户角色
     * @return token
     */
    public static String createToken(String username, String role) {

        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        String token = Jwts
                .builder()
                .setSubject(username)
                //.setClaims(map)
                // .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, APP_SECRET_KEY)
                .compact();
        return TOKEN_PREFIX +token;
    }


    /**
     * 获取当前登录用户用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取当前登录用户角色
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }


    /**
     * 检查token是否过期
     *
     * @param  token token
     * @return boolean
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}


