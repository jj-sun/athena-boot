package com.athena.modules.sys.service.impl;

import com.athena.modules.sys.entity.SysUserRoleEntity;
import com.athena.modules.sys.mapper.SysUserRoleMapper;
import com.athena.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 用户与角色对应关系
 *
 * @author sunjie
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(String userId, List<String> roleIdList) {
		//先删除用户与角色关系
		this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(String roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			this.save(sysUserRoleEntity);
		}
	}

	@Override
	public List<String> queryRoleIdList(String userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(List<String> roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
