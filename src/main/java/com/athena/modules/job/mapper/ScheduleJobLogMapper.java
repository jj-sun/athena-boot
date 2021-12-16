package com.athena.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author sunjie
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
	
}
