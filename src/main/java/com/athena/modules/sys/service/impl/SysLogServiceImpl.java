package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysLogEntity;
import com.athena.modules.sys.mapper.SysLogMapper;
import com.athena.modules.sys.service.SysLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(SysLogEntity sysLog, PageDto pageDto) {

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(pageDto)
        );

        return new PageUtils(page);
    }
}
