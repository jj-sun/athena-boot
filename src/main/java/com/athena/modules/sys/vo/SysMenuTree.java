package com.athena.modules.sys.vo;

import com.athena.common.base.tree.BaseTree;
import com.athena.modules.sys.entity.SysPermissionEntity;
import lombok.Data;

import java.io.Serial;

/**
 * @author sunjie
 * @date 2021/12/20 16:16
 * @description
 */
@Data
public class SysMenuTree extends BaseTree<SysPermissionEntity> {

    @Serial
    private static final long serialVersionUID = -2798455242241355324L;

    public SysMenuTree(SysPermissionEntity sysPermissionEntity) {
        super.setInfo(sysPermissionEntity);
        this.toTree();
    }

    @Override
    public void toTree() {
        SysPermissionEntity sysPermissionEntity = super.getInfo();
        super.setKey(sysPermissionEntity.getId());
        super.setParentKey(sysPermissionEntity.getParentId());
        super.setLabel(sysPermissionEntity.getName());
    }
}
