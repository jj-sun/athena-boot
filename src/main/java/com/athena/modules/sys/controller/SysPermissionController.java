package com.athena.modules.sys.controller;

import com.athena.common.annotation.SysLog;
import com.athena.common.base.tree.BaseTree;
import com.athena.common.constant.Constant;
import com.athena.common.exception.RRException;
import com.athena.common.utils.Result;
import com.athena.modules.sys.entity.SysPermissionEntity;
import com.athena.modules.sys.service.ShiroService;
import com.athena.modules.sys.service.SysPermissionService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author sunjie
 */
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController extends AbstractController {
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public Result nav(){
		List<BaseTree<SysPermissionEntity>> menuTree = sysPermissionService.getUserMenuTree(getUsername());
		Set<String> permissions = shiroService.getUserPermissions(getUsername());
		Map<String, Object> result = Maps.newHashMap();
		result.put("menuTree", menuTree);
		result.put("permissions", permissions);
		return Result.ok(result);
	}
	
	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	//@PreAuthorize("hasAuthority('sys:menu:list')")
	public Result<List<BaseTree<SysPermissionEntity>>> list(){
		List<BaseTree<SysPermissionEntity>> menuList = sysPermissionService.permissionTree();
		return Result.ok(menuList);
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	//@PreAuthorize("hasAuthority('sys:menu:select')")
	public Result<List<SysPermissionEntity>> select(){
		//查询列表数据
		List<SysPermissionEntity> menuList = sysPermissionService.queryNotButtonList();
		
		//添加顶级菜单
		SysPermissionEntity root = new SysPermissionEntity();
		root.setId("0");
		root.setName("一级菜单");
		root.setParentId("-1");
		root.setOpen(true);
		menuList.add(root);
		
		return Result.ok(menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	//@PreAuthorize("hasAuthority('sys:menu:info')")
	public Result<SysPermissionEntity> info(@PathVariable("menuId") Long menuId){
		SysPermissionEntity menu = sysPermissionService.getById(menuId);
		return Result.ok(menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	//@PreAuthorize("hasAuthority('sys:menu:save')")
	public Result<Object> save(@RequestBody SysPermissionEntity menu){
		//数据校验
		verifyForm(menu);
		
		sysPermissionService.save(menu);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	//@PreAuthorize("hasAuthority('sys:menu:update')")
	public Result<Object> update(@RequestBody SysPermissionEntity menu){
		//数据校验
		verifyForm(menu);
				
		sysPermissionService.updateById(menu);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@PostMapping("/delete/{menuId}")
	//@PreAuthorize("hasAuthority('sys:menu:delete')")
	public Result<Object> delete(@PathVariable("menuId") String menuId){

		//判断是否有子菜单或按钮
		List<SysPermissionEntity> menuList = sysPermissionService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return Result.error("请先删除子菜单或按钮");
		}

		sysPermissionService.delete(menuId);

		return Result.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysPermissionEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(!menu.getParentId().equals("0")){
			SysPermissionEntity parentMenu = sysPermissionService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
