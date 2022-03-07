package com.hsx.aclservice.service.impl;

import com.hsx.aclservice.entity.UserInfo;
import com.hsx.aclservice.service.PermissionService;
import com.hsx.aclservice.service.UserInfoService;
import com.hsx.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 登录实现
 *
 * @author HEXIONLY
 * @date 2022/3/7 16:41
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名查询登录
     *
     * @param username 用户名
     * @return userInfo
     * @throws UsernameNotFoundException ex
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.selectUserByUsername(username);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new UsernameNotFoundException("用户不存在。");
        }

        UserInfo curUserInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo, curUserInfo);

        // 根据用户查询权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(userInfo.getId());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
