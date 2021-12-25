package com.athena.modules.sys.controller;

import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * 系统日志
 *
 * @author sunjie
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	//@PreAuthorize("hasAuthority('sys:log:list')")
	public Result<PageUtils> list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);
		return Result.ok(page);
	}
	
}
