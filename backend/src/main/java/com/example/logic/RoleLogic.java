package com.example.logic;

import com.example.entity.Role;
import com.example.service.MenuService;
import com.example.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RoleLogic {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    public List<Role> list() {
        log.info("List all roles");
        return roleService.list();
    }

    public Role getById(Long id) {
        log.info("Get role by id: {}", id);
        return roleService.getById(id);
    }

    public void add(Role role) {
        log.info("Add role: {}", role.getName());
        roleService.add(role);
        log.info("Add role success: {}", role.getId());
    }

    public void update(Role role) {
        log.info("Update role: {}", role.getId());
        roleService.update(role);
        log.info("Update role success: {}", role.getId());
    }

    public void delete(Long id) {
        log.info("Delete role: {}", id);
        roleService.delete(id);
        log.info("Delete role success: {}", id);
    }

    public List<Long> getRoleMenus(Long roleId) {
        log.info("Get role menus: {}", roleId);
        return menuService.getMenuIdsByRoleId(roleId);
    }

    public void updateRoleMenus(Long roleId, List<Long> menuIds) {
        log.info("Update role menus: {}, menuIds: {}", roleId, menuIds);
        menuService.bindMenus(roleId, menuIds);
        log.info("Update role menus success: {}", roleId);
    }

    public List<Long> getRoleUsers(Long roleId) {
        log.info("Get role users: {}", roleId);
        return roleService.getUserIdsByRoleId(roleId);
    }

    public void bindUsers(Long roleId, List<Long> userIds) {
        log.info("Bind users to role: {}, userIds: {}", roleId, userIds);
        roleService.bindUsers(roleId, userIds);
        log.info("Bind users to role success: {}", roleId);
    }

    public void unbindUsers(Long roleId, List<Long> userIds) {
        log.info("Unbind users from role: {}, userIds: {}", roleId, userIds);
        roleService.unbindUsers(roleId, userIds);
        log.info("Unbind users from role success: {}", roleId);
    }
}
