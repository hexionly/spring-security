package com.hsx.security.security;

import com.hsx.utils.utils.ResponseUtil;
import com.hsx.utils.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出处理器
 *
 * @author HEXIONLY
 * @date 2022/3/6 18:17
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;

    private RedisTemplate<String, Object> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 从handler中获取token
     * token不为空移除token，从redis中删除
     *
     * @param request        req
     * @param response       res
     * @param authentication auth
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            tokenManager.removeToken(token);
            String userInfo = tokenManager.getUserInfoFromToken(token);
            redisTemplate.delete(userInfo);
        }
        ResponseUtil.out(response, Result.success());
    }
}
