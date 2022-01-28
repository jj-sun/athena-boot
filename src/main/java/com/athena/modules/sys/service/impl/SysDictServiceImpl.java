package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.common.vo.DictModel;
import com.athena.common.vo.DictModelMany;
import com.athena.modules.sys.entity.SysDict;
import com.athena.modules.sys.mapper.SysDictMapper;
import com.athena.modules.sys.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunjie
 * @date 2022/1/19 19:59
 * @description
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {


    @Override
    public PageUtils queryPage(SysDict dict, PageDto pageDto) {
        IPage<SysDict> page = this.page(
                new Query<SysDict>().getPage(pageDto),
                new LambdaQueryWrapper<SysDict>()
                        .like(StringUtils.isNotBlank(dict.getDictName()), SysDict::getDictName, dict.getDictName())
                        .eq(StringUtils.isNotBlank(dict.getDictCode()), SysDict::getDictCode, dict.getDictCode())
        );
        return new PageUtils(page);
    }

    @Override
    public List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys) {
        return this.getBaseMapper().queryTableDictTextByKeys(table,text,code,keys);
    }

    @Override
    public Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys) {
        List<DictModelMany> list = this.getBaseMapper().queryManyDictByKeys(dictCodeList, keys);
        Map<String, List<DictModel>> dictMap = new HashMap<>();
        for (DictModelMany dict : list) {
            List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
            dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
        }
        return dictMap;
    }
}
