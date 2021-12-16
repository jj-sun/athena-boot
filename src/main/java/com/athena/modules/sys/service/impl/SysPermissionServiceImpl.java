package com.athena.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.athena.common.constant.Constant;
import com.athena.modules.sys.service.SysPermissionService;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.common.utils.MapUtils;
import com.athena.modules.sys.mapper.SysPermissionMapper;
import com.athena.modules.sys.entity.SysPermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("sysMenuService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

	@Autowired
	private SysRolePermissionService sysRolePermissionService;
	
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
	public List<SysPermissionEntity> getUserMenuList(String userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getMenuList(null);
		}
		
		//用户菜单列表
		List<String> menuIdList = this.getBaseMapper().queryAllMenuId(userId);
		return getMenuList(menuIdList);
	}

	/**
	 * 获取拥有的菜单列表
	 * @param menuIdList
	 * @return
	 */
	private List<SysPermissionEntity> getMenuList(List<String> menuIdList) {
		// 查询拥有的所有菜单
		List<SysPermissionEntity> menus = this.baseMapper.selectList(new QueryWrapper<SysPermissionEntity>()
				.in(Objects.nonNull(menuIdList), "menu_id", menuIdList).in("type", 0, 1));
		// 将id和菜单绑定
		HashMap<String, SysPermissionEntity> menuMap = new HashMap<>(12);
		for (SysPermissionEntity s : menus) {
			menuMap.put(s.getId(), s);
		}
		// 使用迭代器,组装菜单的层级关系
		Iterator<SysPermissionEntity> iterator = menus.iterator();
		while (iterator.hasNext()) {
			SysPermissionEntity menu = iterator.next();
			SysPermissionEntity parent = menuMap.get(menu.getParentId());
			if (Objects.nonNull(parent)) {
				parent.getList().add(menu);
				// 将这个菜单从当前节点移除
				iterator.remove();
			}
		}

		return menus;
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
