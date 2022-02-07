package com.athena.modules.sys.service.impl;


import com.athena.common.constant.Constant;
import com.athena.common.utils.TreeUtils;
import com.athena.modules.sys.entity.SysPermissionEntity;
import com.athena.modules.sys.entity.SysRolePermissionEntity;
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


/**
 * @author Mr.sun
 */
@Service("sysMenuService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@Override
	public List<SysPermissionEntity> permissionTree() {
		List<SysPermissionEntity> sysPermissionEntities = this.list();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			List<SysPermissionEntity> roots = sysPermissionEntities.stream().filter(root -> root.getParentId().equals(Constant.TREE_ROOT)).collect(Collectors.toList());
			buildTree(roots, sysPermissionEntities);
			return roots;
		}
		return sysPermissionEntities;
	}
	private void buildTree(List<SysPermissionEntity> roots, List<SysPermissionEntity> all) {
		if(CollectionUtils.isEmpty(roots)) {
			return;
		}
		roots.forEach(root -> {
			List<SysPermissionEntity> childrenList = all.stream().filter(tree -> tree.getParentId().equals(root.getId())).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(childrenList)) {
				root.setChildren(childrenList);
				buildTree(childrenList, all);
			}
		});
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
	public List<SysMenuTree> treeSelect() {
		List<SysPermissionEntity> sysPermissionEntities = this.list();
		return this.getTree(sysPermissionEntities);
	}

	@Override
	public List<SysMenuTree> getUserMenuTree(String username) {
		List<SysPermissionEntity> sysPermissionEntities;
		//系统管理员，拥有最高权限
		if(username.equals(Constant.SUPER_ADMIN)){
			sysPermissionEntities = this.list(new LambdaQueryWrapper<SysPermissionEntity>().ne(SysPermissionEntity::getType, Constant.PermissionType.BUTTON.getValue()));
		} else {
			//用户菜单列表
			List<String> menuIdList = this.getBaseMapper().queryAllMenuId(username);
			sysPermissionEntities = this.list(new LambdaQueryWrapper<SysPermissionEntity>().in(SysPermissionEntity::getId, menuIdList).ne(SysPermissionEntity::getType, Constant.PermissionType.BUTTON.getValue()));
		}
		return this.getTree(sysPermissionEntities);
	}

	private List<SysMenuTree> getTree(List<SysPermissionEntity> sysPermissionEntities) {
		List<SysMenuTree> menuTreeList = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			sysPermissionEntities.forEach(sysPermissionEntity -> menuTreeList.add(new SysMenuTree(sysPermissionEntity)));
			List<SysMenuTree> root = menuTreeList.stream().filter(sysMenuTree -> sysMenuTree.getParentKey().equals(Constant.TREE_ROOT)).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(root)) {
				TreeUtils.buildTree(root, menuTreeList);
				return root;
			}
		}
		return null;
	}

	@Override
	public void delete(String permissionId){
		//删除菜单
		this.removeById(permissionId);
		//删除菜单与角色关联
		sysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermissionEntity>().eq(SysRolePermissionEntity::getPermissionId, permissionId));
	}

}
