package com.athena.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.athena.modules.sys.mapper.SysRolePermissionMapper;
import com.athena.modules.sys.entity.SysRolePermissionEntity;
import com.athena.modules.sys.service.SysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * 角色与菜单对应关系
 *
 * @author sunjie
 */
@Service("sysRoleMenuService")
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermissionEntity> implements SysRolePermissionService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(String roleId, List<String> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new String[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(String menuId : menuIdList){
			SysRolePermissionEntity sysRolePermissionEntity = new SysRolePermissionEntity();
			sysRolePermissionEntity.setId(menuId);
			sysRolePermissionEntity.setRoleId(roleId);

			this.save(sysRolePermissionEntity);
		}
	}

	@Override
	public List<String> queryMenuIdList(String roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(String[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
