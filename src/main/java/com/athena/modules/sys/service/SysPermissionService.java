/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

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

	List<BaseTree<SysPermissionEntity>> permissionTree();

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
	 * 获取不包含按钮的菜单列表
	 */
	List<SysPermissionEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<BaseTree<SysPermissionEntity>> getUserMenuTree(String username);

	/**
	 * 删除
	 */
	void delete(String menuId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String username);
}
