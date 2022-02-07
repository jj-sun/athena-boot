

package com.athena.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.sys.entity.SysPermissionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Mr.sun
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysPermissionEntity> queryListParentId(String parentId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String username);

}
