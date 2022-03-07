package com.hsx.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.aclservice.entity.Role;
import com.hsx.aclservice.mapper.RoleMapper;
import com.hsx.aclservice.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author HEXIONLY
 * @date 2022/3/7 17:11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
