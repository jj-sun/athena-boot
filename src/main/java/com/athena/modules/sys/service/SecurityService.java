package com.athena.modules.sys.service;

import com.athena.modules.sys.entity.SysUserEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Mr.sun
 */
public interface SecurityService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(String username);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(String userId);
}
