package com.athena.modules.sys.service.impl;


import com.athena.common.base.tree.BaseTree;
import com.athena.common.constant.Constant;
import com.athena.common.utils.MapUtils;
import com.athena.common.utils.TreeUtils;
import com.athena.modules.sys.entity.SysPermissionEntity;
import com.athena.modules.sys.mapper.SysPermissionMapper;
import com.athena.modules.sys.service.SysPermissionService;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.modules.sys.vo.SysMenuTree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@Override
	public List<BaseTree<SysPermissionEntity>> permissionTree() {
		List<SysPermissionEntity> sysPermissionEntities = this.list();
		List<BaseTree<SysPermissionEntity>> menuTreeList = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			sysPermissionEntities.forEach(sysPermissionEntity -> menuTreeList.add(new SysMenuTree(sysPermissionEntity)));
		}
		return TreeUtils.buildTree(menuTreeList);
	}

	@Override
	public List<SysPermissionEntity> queryListParentId(String parentId, List<String> menuIdList) {
		List<SysPermissionEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysPermissionEntity> userMenuList = new ArrayList<>();
		for(SysPermissionEntity menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysPermissionEntity> queryListParentId(String parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysPermissionEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<BaseTree<SysPermissionEntity>> getUserMenuTree(String username) {
		List<SysPermissionEntity> sysPermissionEntities;
		//系统管理员，拥有最高权限
		if(username.equals(Constant.SUPER_ADMIN)){
			sysPermissionEntities = this.list(new LambdaQueryWrapper<SysPermissionEntity>().ne(SysPermissionEntity::getType, Constant.PermissionType.BUTTON.getValue()));
		} else {
			//用户菜单列表
			List<String> menuIdList = this.getBaseMapper().queryAllMenuId(username);
			sysPermissionEntities = this.list(new LambdaQueryWrapper<SysPermissionEntity>().in(SysPermissionEntity::getId, menuIdList).ne(SysPermissionEntity::getType, Constant.PermissionType.BUTTON.getValue()));
		}

		List<BaseTree<SysPermissionEntity>> menuTreeList = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			sysPermissionEntities.forEach(sysPermissionEntity -> menuTreeList.add(new SysMenuTree(sysPermissionEntity)));
			List<BaseTree<SysPermissionEntity>> root = menuTreeList.stream().filter(sysMenuTree -> sysMenuTree.getParentKey().equals(Constant.TREE_ROOT)).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(root)) {
				TreeUtils.buildTree(root, menuTreeList);
				return root;
			}
		}
		return null;
	}

	@Override
	public void delete(String menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		sysRolePermissionService.removeByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysPermissionEntity> getAllMenuList(List<String> menuIdList){
		//查询根菜单列表
		List<SysPermissionEntity> menuList = queryListParentId("0", menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysPermissionEntity> getMenuTreeList(List<SysPermissionEntity> menuList, List<String> menuIdList){
		List<SysPermissionEntity> subMenuList = new ArrayList<SysPermissionEntity>();
		
		for(SysPermissionEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

	@Override
	public List<String> queryAllMenuId(String username) {
		return null;
	}
}
