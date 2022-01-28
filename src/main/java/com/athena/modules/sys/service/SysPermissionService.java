

package com.athena.modules.sys.service;


import com.athena.common.base.tree.BaseTree;
import com.athena.modules.sys.entity.SysPermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单管理
 *
 * @author sunjie
 */
public interface SysPermissionService extends IService<SysPermissionEntity> {

	List<SysPermissionEntity> permissionTree();

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysPermissionEntity> queryListParentId(String parentId, List<String> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysPermissionEntity> queryListParentId(String parentId);
	
	/**
	 * 获取菜单
	 */
	List<BaseTree<SysPermissionEntity>> treeSelect();
	
	/**
	 * 获取用户菜单列表
	 */
	List<BaseTree<SysPermissionEntity>> getUserMenuTree(String username);

	/**
	 * 删除
	 */
	void delete(String permissionId);

}
