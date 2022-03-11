package com.hsx.security.config;

import com.hsx.security.filter.TokenAuthenticationFilter;
import com.hsx.security.filter.TokenLoginFilter;
import com.hsx.security.security.DefaultPasswordEncode;
import com.hsx.security.security.TokenLogoutHandler;
import com.hsx.security.security.TokenManager;
import com.hsx.security.security.UnauthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * security 配置类
 *
 * @author HEXIONLY
 * @date 2022/3/7 16:00
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 管理token
     */
    private TokenManager tokenManager;

    /**
     * 管理redis
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 密码
     */
    private DefaultPasswordEncode passwordEncode;

    /**
     * 登录接口
     */
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate,
                             DefaultPasswordEncode passwordEncode, UserDetailsService userDetailsService) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.passwordEncode = passwordEncode;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 设置退出地址，token，redis操作地址
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable()
                // 请求
                .authorizeRequests()
                // 权限
                .anyRequest().authenticated()
                // 退出
                .and().logout().logoutUrl("/admin/acl/index/logout")
                // t推出执行退出处理器
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
                // 登录权限，认证过滤器
                .and()
                // 认证
                .addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManager()))
                // 权限
                .addFilter(new TokenAuthenticationFilter(tokenManager, redisTemplate, authenticationManager())).httpBasic().and().exceptionHandling()
                // 没有权限时，调用自定义未受权受理类
                .authenticationEntryPoint(new UnauthEntryPoint());
    }

    /**
     * 设置调用哪一个userDetails，使用什么密码加密方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncode);
    }

    /**
     * 设置不需要认证访问的路径
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/api/**");
    }
}
