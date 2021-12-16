package com.athena.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author sunjie
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
