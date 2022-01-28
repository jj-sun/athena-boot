

package com.athena.modules.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @author sunjie
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {
	
}
