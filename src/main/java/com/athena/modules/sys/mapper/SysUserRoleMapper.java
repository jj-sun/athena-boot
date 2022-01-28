

package com.athena.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author sunjie
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<String> queryRoleIdList(String userId);


	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(List<String> roleIds);
}
