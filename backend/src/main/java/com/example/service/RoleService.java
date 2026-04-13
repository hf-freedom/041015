package com.example.service;

import com.example.cache.LocalCache;
import com.example.entity.Role;
import com.example.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LocalCache localCache;

    public List<Role> list() {
        return roleMapper.selectAll();
    }

    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    public void add(Role role) {
        role.setId(localCache.generateRoleId());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insert(role);
    }

    public void update(Role role) {
        Role existing = roleMapper.selectById(role.getId());
        if (existing != null) {
            role.setCreateTime(existing.getCreateTime());
            role.setUpdateTime(LocalDateTime.now());
            roleMapper.update(role);
        }
    }

    public void delete(Long id) {
        roleMapper.deleteById(id);
    }

    public void bindUsers(Long roleId, List<Long> userIds) {
        for (Long userId : userIds) {
            roleMapper.insertUserRole(userId, roleId);
        }
    }

    public void unbindUsers(Long roleId, List<Long> userIds) {
        for (Long userId : userIds) {
            roleMapper.deleteUserRole(userId, roleId);
        }
    }

    public List<Long> getUserIdsByRoleId(Long roleId) {
        return roleMapper.selectUserIdsByRoleId(roleId);
    }

    public List<Long> getRoleIdsByUserId(Long userId) {
        return roleMapper.selectRoleIdsByUserId(userId);
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        roleMapper.clearUserRoles(userId);
        for (Long roleId : roleIds) {
            roleMapper.insertUserRole(userId, roleId);
        }
    }
}
