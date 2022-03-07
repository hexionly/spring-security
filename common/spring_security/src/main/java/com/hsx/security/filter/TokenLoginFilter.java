package com.hsx.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsx.security.entity.SecurityUser;
import com.hsx.security.entity.User;
import com.hsx.security.security.TokenManager;
import com.hsx.utils.utils.ResponseUtil;
import com.hsx.utils.utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 认证过滤器
 *
 * @author HEXIONLY
 * @date 2022/3/7 14:35
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 管理token
     */
    private TokenManager tokenManager;

    /**
     * 管理redis
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 权限管理
     */
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate,
                            AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        // 是否仅支持post提交
        this.setPostOnly(false);
        // 设置登录路径和提交类型
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
    }

    /**
     * 获取表单提交用户名和密码
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 获取表单提交的数据
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            /*
            new UsernamePasswordAuthenticationToken(param1, param2, param3)
            param1: 用户名
            param2：密码
            param3: 权限
             */
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 认证成功调用方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException,
            ServletException {
        // 认证后的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        // 认证成功根据用户名生成toekn
        String token = tokenManager.createToken(user.getUsername());
        // 把用户权限和用户名称放到redis,根据名称查找权限
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());
        // 返回token
        ResponseUtil.out(response, Result.success().data("token", token));
    }

    /**
     * 认证失败调用方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, Result.error().message("登录失败！"));
    }
}
