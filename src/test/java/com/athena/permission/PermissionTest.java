package com.athena.permission;

import com.athena.modules.sys.entity.SysPermissionEntity;
import com.athena.modules.sys.service.SysPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.sun
 * @date 2021/12/28 11:49
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionTest {

    @Autowired
    private SysPermissionService permissionService;

    @Test
    public void inertRoot() {
        SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
        sysPermissionEntity.setParentId("0");
        sysPermissionEntity.setName("首页");
        sysPermissionEntity.setUrl("dashboard/analysis");
        sysPermissionEntity.setComponent("/dashboard/index");
        sysPermissionEntity.setType(0);

        permissionService.save(sysPermissionEntity);
    }

    @Test
    public void insertSystem(){
        SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
        sysPermissionEntity.setParentId("0");
        sysPermissionEntity.setName("系统管理");
        sysPermissionEntity.setUrl("/system");
        sysPermissionEntity.setComponent("layout/index");
        sysPermissionEntity.setType(1);
        permissionService.save(sysPermissionEntity);
    }

    public List<SysPermissionEntity> buildSystemMenu() {
        List<SysPermissionEntity> list = new ArrayList<>();
        SysPermissionEntity user = new SysPermissionEntity();
        user.setParentId("1475702165283561473");
        user.setName("用户管理");
        user.setUrl("/system/user");
        user.setComponent("system/userList");
        user.setType(0);

        SysPermissionEntity role = new SysPermissionEntity();
        role.setParentId("1475702165283561473");
        role.setName("角色管理");
        role.setUrl("/system/role");
        role.setComponent("system/roleList");
        role.setType(1);

        SysPermissionEntity menu = new SysPermissionEntity();
        menu.setParentId("1475702165283561473");
        menu.setName("菜单管理");
        menu.setUrl("/system/permission");
        menu.setComponent("system/permissionList");
        menu.setType(2);

        list.add(user);
        list.add(role);
        list.add(menu);
        return list;
    }

    @Test
    public void insertSystemChildren() {
        List<SysPermissionEntity> list = buildSystemMenu();
        permissionService.saveBatch(list);
    }

}
