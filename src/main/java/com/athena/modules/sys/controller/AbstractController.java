package com.athena.modules.sys.controller;

import com.athena.modules.sys.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author sunjie
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return new SysUserEntity();
	}

	protected String getUserId() {
		return getUser().getId();
	}

	protected String getUsername() {
		return "";
	}
}
