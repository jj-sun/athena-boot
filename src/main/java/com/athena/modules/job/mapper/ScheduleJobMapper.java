package com.athena.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author sunjie
 */
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
