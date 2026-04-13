package com.example.logic;

import com.example.cache.LocalCache;
import com.example.entity.Role;
import com.example.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleLogic {

    private static final Logger logger = LoggerFactory.getLogger(RoleLogic.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocalCache localCache;

    public List<Role> listRoles() {
        logger.debug("Fetching all roles");
        return roleService.list();
    }

    public Role getRoleById(Long id) {
        logger.debug("Fetching role by id: {}", id);
        return roleService.getById(id);
    }

    public void addRole(Role role) {
        logger.info("Adding new role: {}", role.getName());
        role.setId(localCache.generateRoleId());
        roleService.add(role);
        logger.info("Role added successfully: {} (ID: {})", role.getName(), role.getId());
    }

    public void updateRole(Role role) {
        logger.info("Updating role: {}", role.getId());
        roleService.update(role);
        logger.info("Role updated successfully: {}", role.getId());
    }

    public void deleteRole(Long id) {
        logger.info("Deleting role: {}", id);
        roleService.delete(id);
        logger.info("Role deleted successfully: {}", id);
    }

    public void bindUsers(Long roleId, List<Long> userIds) {
        logger.info("Binding users to role {}: {}", roleId, userIds);
        roleService.bindUsers(roleId, userIds);
        logger.info("Users bound to role {} successfully", roleId);
    }

    public void unbindUsers(Long roleId, List<Long> userIds) {
        logger.info("Unbinding users from role {}: {}", roleId, userIds);
        roleService.unbindUsers(roleId, userIds);
        logger.info("Users unbound from role {} successfully", roleId);
    }

    public List<Long> getUserIdsByRoleId(Long roleId) {
        logger.debug("Getting user ids for role: {}", roleId);
        return roleService.getUserIdsByRoleId(roleId);
    }

    public List<Long> getRoleIdsByUserId(Long userId) {
        logger.debug("Getting role ids for user: {}", userId);
        return roleService.getRoleIdsByUserId(userId);
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        logger.info("Updating roles for user {}: {}", userId, roleIds);
        roleService.updateUserRoles(userId, roleIds);
        logger.info("User roles updated successfully for user: {}", userId);
    }
}
