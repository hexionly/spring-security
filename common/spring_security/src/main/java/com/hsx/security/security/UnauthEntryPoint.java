package com.hsx.security.security;

import com.hsx.utils.utils.ResponseUtil;
import com.hsx.utils.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未受权统一受理类
 *
 * @author HEXIONLY
 * @date 2022/3/7 14:31
 */
public class UnauthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, Result.error().message("你没有权限访问，请联系管理员。"));
    }
}
