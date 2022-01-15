package com.athena.modules.sys.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 系统用户
 *
 * @author sunjie
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(SysUserEntity user, PageDto pageDto);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(String userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 保存用户
	 */
	void saveUser(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(List<String> ids);

	/**
	 * 修改密码
	 * @param username       用户ID
	 * @param newPassword  新密码
	 */
	boolean updatePassword(String username, String newPassword);

}
