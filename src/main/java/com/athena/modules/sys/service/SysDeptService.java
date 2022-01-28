package com.athena.modules.sys.service;

import com.athena.common.base.tree.BaseTree;
import com.athena.modules.sys.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author sunjie
 * @date 2022/1/24 9:01
 * @description
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 部门树形列表
     * @return
     */
    List<SysDept> deptTreeList();

    /**
     * 树形选择
     * @return
     */
    List<BaseTree<SysDept>> treeSelect();

    boolean deleteBatch(List<String> ids);
}
