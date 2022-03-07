package com.hsx.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.aclservice.entity.Permission;
import com.hsx.aclservice.entity.UserInfo;
import com.hsx.aclservice.mapper.PermissionMapper;
import com.hsx.aclservice.service.PermissionService;
import com.hsx.aclservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:10
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户查询权限列表
     *
     * @param id userId
     * @return
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> permissionValueList = null;

        // 管理员可以访问所有，你不是就查看指定
        if (this.isSysAdmin(id)) {
            permissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            permissionValueList = baseMapper.selectAllPermissionValueById(id);
        }
        return permissionValueList;
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        UserInfo userInfo = userInfoService.getById(userId);

        return null != userInfo && "admin".equals(userInfo.getUsername());
    }
}
