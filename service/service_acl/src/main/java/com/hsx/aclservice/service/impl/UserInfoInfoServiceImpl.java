package com.hsx.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.aclservice.entity.UserInfo;
import com.hsx.aclservice.mapper.UserInfoMapper;
import com.hsx.aclservice.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:12
 */
@Service
public class UserInfoInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return user
     */
    @Override
    public UserInfo selectUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username));
    }
}
