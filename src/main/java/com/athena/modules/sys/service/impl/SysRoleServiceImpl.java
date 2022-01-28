package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysRoleEntity;
import com.athena.modules.sys.mapper.SysRoleMapper;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.modules.sys.service.SysRoleService;
import com.athena.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色
 *
 * @author sunjie
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(SysRoleEntity role, PageDto pageDto) {

		IPage<SysRoleEntity> page = this.page(
			new Query<SysRoleEntity>().getPage(pageDto)
		);

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteEntity(String id) {
		this.removeById(id);
		//删除角色与菜单关联
		sysRolePermissionService.deleteBatch(List.of(id));

		//删除角色与用户关联
		sysUserRoleService.deleteBatch(List.of(id));
		return false;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> ids) {
        //删除角色
        this.removeByIds(ids);

        //删除角色与菜单关联
        sysRolePermissionService.deleteBatch(ids);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(ids);
    }


    @Override
	public List<String> queryRoleIdList(String username) {
		return baseMapper.queryRoleIdList(username);
	}

}
