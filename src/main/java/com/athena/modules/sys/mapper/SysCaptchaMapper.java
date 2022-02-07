package com.athena.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.athena.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author Mr.sun
 */
@Mapper
public interface SysCaptchaMapper extends BaseMapper<SysCaptchaEntity> {

}
