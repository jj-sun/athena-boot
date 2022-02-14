package com.athena.modules.job.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.constant.Constant;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.job.entity.ScheduleJob;
import com.athena.modules.job.mapper.ScheduleJobMapper;
import com.athena.modules.job.service.ScheduleJobService;
import com.athena.modules.job.utils.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.sun
 */
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJob> scheduleJobList = this.list();
		for(ScheduleJob scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}

	@Override
	public PageUtils queryPage(ScheduleJob scheduleJob, PageDto pageDto) {

		IPage<ScheduleJob> page = this.page(
			new Query<ScheduleJob>().getPage(pageDto),
			new LambdaQueryWrapper<ScheduleJob>().like(StringUtils.isNotBlank(scheduleJob.getBeanName()), ScheduleJob::getBeanName, scheduleJob.getBeanName())
		);

		return new PageUtils(page);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJob(ScheduleJob scheduleJob) {
		scheduleJob.setCtime(new Date());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                
        this.updateById(scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	this.removeByIds(jobIds);
	}

	@Override
    public int updateBatch(List<String> jobIds, int status){
    	Map<String, Object> map = new HashMap<>(2);
    	map.put("list", jobIds);
    	map.put("status", status);
    	return baseMapper.updateBatch(map);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.run(scheduler, this.getById(jobId));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(List<String> jobIds) {
        for(String jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
    
}
