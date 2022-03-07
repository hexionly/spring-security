package com.hsx.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsx.aclservice.entity.UserInfo;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:12
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return user
     */
    UserInfo selectUserByUsername(String username);
}
