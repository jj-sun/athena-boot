package com.athena.modules.job.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 定时任务日志
 *
 * @author Mr.sun
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(ScheduleJobLogEntity scheduleJobLog, PageDto pageDto);
	
}
