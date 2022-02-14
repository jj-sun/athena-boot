package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysUser;
import com.athena.modules.sys.entity.SysUserRole;
import com.athena.modules.sys.mapper.SysUserMapper;
import com.athena.modules.sys.service.SysUserRoleService;
import com.athena.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 系统用户
 *
 * @author Mr.sun
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(SysUser user, PageDto pageDto) {

		IPage<SysUser> page = this.page(
			new Query<SysUser>().getPage(pageDto),
			new LambdaQueryWrapper<SysUser>()
				.like(StringUtils.isNotBlank(user.getUsername()), SysUser::getUsername, user.getUsername())
				.like(StringUtils.isNotBlank(user.getRealname()), SysUser::getRealname, user.getRealname())
		);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(String username) {
		return baseMapper.queryAllPerms(username);
	}

	@Override
	public SysUser queryByUserName(String username) {
		return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(SysUser user) {
		//sha256加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(StringUtils.isBlank(user.getPassword())) {
			user.setPassword(passwordEncoder.encode("123456"));
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		this.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUser user) {

		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Override
	public boolean deleteEntity(String id) {
		this.removeById(id);
		sysUserRoleService.saveOrUpdate(id, null);
		return false;
	}

	@Override
	public void deleteBatch(List<String> ids) {
		this.removeByIds(ids);
		sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, ids));
	}

	@Override
	public boolean updatePassword(String username, String newPassword) {
		SysUser userEntity = new SysUser();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUser>().eq("username", username));
	}


}