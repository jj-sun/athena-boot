package com.athena.modules.sys.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色
 *
 * @author sunjie
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(SysRoleEntity role, PageDto pageDto);

	boolean deleteEntity(String id);

	void deleteBatch(List<String> ids);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<String> queryRoleIdList(String username);
}
