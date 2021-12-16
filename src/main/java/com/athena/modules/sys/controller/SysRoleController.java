package com.athena.modules.sys.controller;

import com.athena.common.annotation.SysLog;
import com.athena.common.constant.Constant;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.common.validator.ValidatorUtils;
import com.athena.modules.sys.entity.SysRoleEntity;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author sunjie
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	//@RequiresPermissions("sys:role:list")
	public Result<PageUtils> list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		PageUtils page = sysRoleService.queryPage(params);

		return Result.ok(page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	//@RequiresPermissions("sys:role:select")
	public Result<List<SysRoleEntity>> select(){
		Map<String, Object> map = new HashMap<>();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("create_user_id", getUserId());
		}
		List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
		
		return Result.ok(list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	//@RequiresPermissions("sys:role:info")
	public Result<SysRoleEntity> info(@PathVariable("roleId") String roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<String> menuIdList = sysRolePermissionService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return Result.ok(role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	//@RequiresPermissions("sys:role:save")
	public Result<Object> save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.saveRole(role);
		
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	//@RequiresPermissions("sys:role:update")
	public Result<Object> update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	//@RequiresPermissions("sys:role:delete")
	public Result<Object> delete(@RequestBody String[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}