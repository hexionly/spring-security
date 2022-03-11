package com.hsx.aclservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsx.aclservice.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:07
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 管理员时的权限
     *
     * @return
     */
    List<String> selectAllPermissionValue();

    /**
     * 普通用户使得权限查询
     *
     * @param userId
     * @return
     */
    List<String> selectAllPermissionValueById(String userId);
}
