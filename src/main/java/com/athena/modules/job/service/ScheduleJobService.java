

package com.athena.modules.job.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 定时任务
 *
 * @author Mr.sun
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	PageUtils queryPage(ScheduleJobEntity scheduleJob, PageDto pageDto);

	/**
	 * 保存定时任务
	 */
	void saveJob(ScheduleJobEntity scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(String[] jobIds);

	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(String[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(String[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(String[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(String[] jobIds);
}
