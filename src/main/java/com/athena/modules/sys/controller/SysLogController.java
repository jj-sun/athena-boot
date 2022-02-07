package com.athena.modules.sys.controller;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.modules.sys.entity.SysLogEntity;
import com.athena.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 系统日志
 *
 * @author Mr.sun
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
	public Result<PageUtils> list(SysLogEntity sysLog, PageDto pageDto){
		PageUtils page = sysLogService.queryPage(sysLog, pageDto);
		return Result.ok(page);
	}
	
}
