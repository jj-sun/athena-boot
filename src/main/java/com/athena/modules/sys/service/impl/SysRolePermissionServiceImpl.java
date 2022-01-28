package com.athena.modules.sys.service.impl;

import com.athena.modules.sys.entity.SysRolePermissionEntity;
import com.athena.modules.sys.mapper.SysRolePermissionMapper;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
	public void saveOrUpdate(String roleId, List<String> permissionIds) {
		//先删除角色与菜单关系
		deleteBatch(List.of(roleId));

		if(permissionIds.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(String permissionId : permissionIds){
			SysRolePermissionEntity sysRolePermissionEntity = new SysRolePermissionEntity();
			sysRolePermissionEntity.setPermissionId(permissionId);
			sysRolePermissionEntity.setRoleId(roleId);

			this.save(sysRolePermissionEntity);
		}
	}

	@Override
	public List<String> queryPermissionIdList(String roleId) {
		return baseMapper.queryPermissionIdList(roleId);
	}

	@Override
	public int deleteBatch(List<String> roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
