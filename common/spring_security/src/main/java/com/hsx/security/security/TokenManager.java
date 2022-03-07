package com.hsx.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token操作工具类
 *
 * @author HEXIONLY
 * @date 2022/3/6 17:52
 */
@Component
public class TokenManager {

    /**
     * 有效时长
     */
    private final long TOKEN_EFFECTIVE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 私钥
     */
    private final String TOKEN_SIGN_KEY = "HEXIONLY";

    /**
     * 根据用户名生成token
     *
     * @param username 用户名
     * @return token
     */
    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EFFECTIVE_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    public String getUserInfoFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SIGN_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 移除token
     * @param token token
     */
    public void removeToken(String token) { }
}
