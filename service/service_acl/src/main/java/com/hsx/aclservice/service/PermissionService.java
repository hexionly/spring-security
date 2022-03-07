package com.hsx.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsx.aclservice.entity.Permission;

import java.util.List;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:11
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户查询权限列表
     * @param id userId
     * @return
     */
    List<String> selectPermissionValueByUserId(String id);
}
