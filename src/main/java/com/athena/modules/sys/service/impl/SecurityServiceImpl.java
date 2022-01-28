package com.athena.modules.sys.service.impl;

import com.athena.common.constant.Constant;
import com.athena.modules.sys.entity.SysPermissionEntity;
import com.athena.modules.sys.entity.SysUserEntity;
import com.athena.modules.sys.mapper.SysPermissionMapper;
import com.athena.modules.sys.mapper.SysUserMapper;
import com.athena.modules.sys.service.SecurityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sunjie
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Set<String> getUserPermissions(String username) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(username.equals(Constant.SUPER_ADMIN)){
            List<SysPermissionEntity> menuList = sysPermissionMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysPermissionEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(username);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserEntity queryUser(String userId) {
        return sysUserMapper.selectById(userId);
    }
}