package com.athena.modules.sys.controller;

import com.athena.modules.sys.entity.SysUserEntity;
import com.athena.modules.sys.form.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller公共组件
 *
 * @author sunjie
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	protected String getUserId() {
		return getUser().getId();
	}

	protected String getUsername() {
		return getUser().getUsername();
	}
}
