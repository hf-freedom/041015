package com.example.logic;

import com.example.entity.Role;
import com.example.service.MenuService;
import com.example.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleLogic {

    private static final Logger log = LoggerFactory.getLogger(RoleLogic.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    public List<Role> list() {
        log.info("[Role] 查询角色列表开始");
        try {
            List<Role> roles = roleService.list();
            log.info("[Role] 查询角色列表成功, 数量: {}", roles.size());
            return roles;
        } catch (Exception e) {
            log.error("[Role] 查询角色列表异常", e);
            throw e;
        }
    }

    public Role getById(Long id) {
        log.info("[Role] 查询角色详情开始, id: {}", id);
        try {
            Role role = roleService.getById(id);
            log.info("[Role] 查询角色详情成功, id: {}", id);
            return role;
        } catch (Exception e) {
            log.error("[Role] 查询角色详情异常, id: {}", id, e);
            throw e;
        }
    }

    public void add(Role role) {
        log.info("[Role] 添加角色开始, name: {}", role.getName());
        try {
            roleService.add(role);
            log.info("[Role] 添加角色成功, name: {}", role.getName());
        } catch (Exception e) {
            log.error("[Role] 添加角色异常, name: {}", role.getName(), e);
            throw e;
        }
    }

    public void update(Role role) {
        log.info("[Role] 更新角色开始, id: {}", role.getId());
        try {
            roleService.update(role);
            log.info("[Role] 更新角色成功, id: {}", role.getId());
        } catch (Exception e) {
            log.error("[Role] 更新角色异常, id: {}", role.getId(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        log.info("[Role] 删除角色开始, id: {}", id);
        try {
            roleService.delete(id);
            log.info("[Role] 删除角色成功, id: {}", id);
        } catch (Exception e) {
            log.error("[Role] 删除角色异常, id: {}", id, e);
            throw e;
        }
    }

    public List<Long> getRoleMenus(Long roleId) {
        log.info("[Role] 查询角色菜单开始, roleId: {}", roleId);
        try {
            List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);
            log.info("[Role] 查询角色菜单成功, roleId: {}", roleId);
            return menuIds;
        } catch (Exception e) {
            log.error("[Role] 查询角色菜单异常, roleId: {}", roleId, e);
            throw e;
        }
    }

    public void updateRoleMenus(Long roleId, List<Long> menuIds) {
        log.info("[Role] 更新角色菜单开始, roleId: {}, menuIds: {}", roleId, menuIds);
        try {
            menuService.bindMenus(roleId, menuIds);
            log.info("[Role] 更新角色菜单成功, roleId: {}", roleId);
        } catch (Exception e) {
            log.error("[Role] 更新角色菜单异常, roleId: {}", roleId, e);
            throw e;
        }
    }

    public List<Long> getRoleUsers(Long roleId) {
        log.info("[Role] 查询角色用户开始, roleId: {}", roleId);
        try {
            List<Long> userIds = roleService.getUserIdsByRoleId(roleId);
            log.info("[Role] 查询角色用户成功, roleId: {}", roleId);
            return userIds;
        } catch (Exception e) {
            log.error("[Role] 查询角色用户异常, roleId: {}", roleId, e);
            throw e;
        }
    }

    public void bindUsers(Long roleId, List<Long> userIds) {
        log.info("[Role] 绑定用户开始, roleId: {}, userIds: {}", roleId, userIds);
        try {
            roleService.bindUsers(roleId, userIds);
            log.info("[Role] 绑定用户成功, roleId: {}", roleId);
        } catch (Exception e) {
            log.error("[Role] 绑定用户异常, roleId: {}", roleId, e);
            throw e;
        }
    }

    public void unbindUsers(Long roleId, List<Long> userIds) {
        log.info("[Role] 解绑用户开始, roleId: {}, userIds: {}", roleId, userIds);
        try {
            roleService.unbindUsers(roleId, userIds);
            log.info("[Role] 解绑用户成功, roleId: {}", roleId);
        } catch (Exception e) {
            log.error("[Role] 解绑用户异常, roleId: {}", roleId, e);
            throw e;
        }
    }
}
