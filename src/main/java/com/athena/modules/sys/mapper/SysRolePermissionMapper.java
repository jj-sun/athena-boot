package com.athena.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.sys.entity.SysRolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author sunjie
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermissionEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<String> queryPermissionIdList(String roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(List<String> roleIds);
}
