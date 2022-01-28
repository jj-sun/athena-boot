package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysUserEntity;
import com.athena.modules.sys.entity.SysUserRoleEntity;
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
 * @author sunjie
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(SysUserEntity user, PageDto pageDto) {

		IPage<SysUserEntity> page = this.page(
			new Query<SysUserEntity>().getPage(pageDto),
			new LambdaQueryWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(user.getUsername()),SysUserEntity::getUsername, user.getUsername())
				.like(StringUtils.isNotBlank(user.getRealname()), SysUserEntity::getRealname, user.getRealname())
		);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(String userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(SysUserEntity user) {
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
	public void update(SysUserEntity user) {

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
		sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId, ids));
	}

	@Override
	public boolean updatePassword(String username, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("username", username));
	}


}