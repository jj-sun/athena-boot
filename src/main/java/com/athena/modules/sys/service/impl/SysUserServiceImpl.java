package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.constant.Constant;
import com.athena.common.exception.RRException;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysUserEntity;
import com.athena.modules.sys.mapper.SysUserMapper;
import com.athena.modules.sys.service.SysRoleService;
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
	@Autowired
	private SysRoleService sysRoleService;

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
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		/*if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}*/
		this.updateById(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(List<String> ids) {
		this.removeByIds(ids);
	}

	@Override
	public boolean updatePassword(String username, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("username", username));
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreator().equals(Constant.SUPER_ADMIN)){
			return ;
		}
		
		//查询用户创建的角色列表
		List<String> roleIdList = sysRoleService.queryRoleIdList(user.getCreator());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

}