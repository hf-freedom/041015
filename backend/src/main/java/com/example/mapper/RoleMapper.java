package com.example.mapper;

import com.example.cache.LocalCache;
import com.example.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleMapper {

    @Autowired
    private LocalCache localCache;

    public void insert(Role role) {
        localCache.saveRole(role);
    }

    public void update(Role role) {
        localCache.saveRole(role);
    }

    public void deleteById(Long id) {
        localCache.deleteRole(id);
    }

    public Role selectById(Long id) {
        return localCache.getRoleById(id);
    }

    public List<Role> selectAll() {
        return localCache.getAllRoles();
    }

    public void insertUserRole(Long userId, Long roleId) {
        localCache.saveUserRole(userId, roleId);
    }

    public void deleteUserRole(Long userId, Long roleId) {
        localCache.deleteUserRole(userId, roleId);
    }

    public List<Long> selectRoleIdsByUserId(Long userId) {
        return localCache.getRoleIdsByUserId(userId);
    }

    public List<Long> selectUserIdsByRoleId(Long roleId) {
        return localCache.getUserIdsByRoleId(roleId);
    }

    public void clearUserRoles(Long userId) {
        localCache.clearUserRoles(userId);
    }
}
