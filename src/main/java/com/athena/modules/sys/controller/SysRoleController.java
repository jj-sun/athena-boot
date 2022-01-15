package com.athena.modules.sys.controller;

import com.athena.common.annotation.SysLog;
import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.common.validator.ValidatorUtils;
import com.athena.modules.sys.entity.SysRoleEntity;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	//@PreAuthorize("hasAuthority('sys:role:list')")
	public Result<PageUtils> list(SysRoleEntity role, PageDto pageDto){

		PageUtils page = sysRoleService.queryPage(role, pageDto);

		return Result.ok(page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	//@PreAuthorize("hasAuthority('sys:role:select')")
	public Result<List<SysRoleEntity>> select(){
		Map<String, Object> map = new HashMap<>();

		List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
		
		return Result.ok(list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	//@PreAuthorize("hasAuthority('sys:role:info')")
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
	//@PreAuthorize("hasAuthority('sys:role:save')")
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
	//@PreAuthorize("hasAuthority('sys:role:update')")
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
	//@PreAuthorize("hasAuthority('sys:role:delete')")
	public Result<Object> delete(@RequestBody String[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}
