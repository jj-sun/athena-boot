package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 *
 * @author sunjie
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class SysRoleEntity extends BasePo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;


	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist=false)
	private List<String> menuIdList;

}
