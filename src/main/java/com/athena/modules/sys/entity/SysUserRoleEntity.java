package com.athena.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author sunjie
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@TableId
	private String id;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 角色ID
	 */
	private String roleId;

	
}
