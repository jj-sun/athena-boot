package com.athena.modules.job.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.job.entity.ScheduleJobLogEntity;
import com.athena.modules.job.mapper.ScheduleJobLogMapper;
import com.athena.modules.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(ScheduleJobLogEntity scheduleJobLog, PageDto pageDto) {

		IPage<ScheduleJobLogEntity> page = this.page(
			new Query<ScheduleJobLogEntity>().getPage(pageDto),
			new QueryWrapper<ScheduleJobLogEntity>()
		);

		return new PageUtils(page);
	}

}
