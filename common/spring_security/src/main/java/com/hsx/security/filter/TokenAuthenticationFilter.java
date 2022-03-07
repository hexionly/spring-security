package com.hsx.security.filter;

import com.hsx.security.security.TokenManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 授权过滤器
 *
 * @author HEXIONLY
 * @date 2022/3/7 14:33
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    /**
     * 管理token
     */
    private TokenManager tokenManager;

    /**
     * 管理redis
     */
    private RedisTemplate<String, Object> redisTemplate;

    public TokenAuthenticationFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate,
                                     AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取当前认证成功用户信息
        UsernamePasswordAuthenticationToken authResult = getAuthenticationEntryPoint(request);
        // 判断权限信息，有就放到权限上下文中
        if (!ObjectUtils.isEmpty(authResult)) {
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationEntryPoint(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            // 获取token中的用户名
            String username = tokenManager.getUserInfoFromToken(token);
            // 从redis中拿到权限
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(username);

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // 将list<String>转换成Collection<GrantedAuthority>
            if (!ObjectUtils.isEmpty(permissionValueList)) {
                permissionValueList.forEach(element -> authorities.add(new SimpleGrantedAuthority(element)));
            }

            return new UsernamePasswordAuthenticationToken(username, token, authorities);
        }
        return null;
    }
}
