package com.example.logic;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserLogic {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public Map<String, Object> login(String username, String password) {
        log.info("User login attempt: {}", username);
        Map<String, Object> result = userService.login(username, password);
        if (result != null) {
            log.info("User login success: {}", username);
        } else {
            log.warn("User login failed: {}", username);
        }
        return result;
    }

    public User getById(Long id) {
        log.info("Get user by id: {}", id);
        return userService.getById(id);
    }

    public List<User> list() {
        log.info("List all users");
        return userService.list();
    }

    public void add(User user) {
        log.info("Add user: {}", user.getUsername());
        userService.add(user);
        log.info("Add user success: {}", user.getId());
    }

    public void update(User user) {
        log.info("Update user: {}", user.getId());
        userService.update(user);
        log.info("Update user success: {}", user.getId());
    }

    public void delete(Long id) {
        log.info("Delete user: {}", id);
        userService.delete(id);
        log.info("Delete user success: {}", id);
    }

    public void updateProfile(User user) {
        log.info("Update user profile: {}", user.getId());
        userService.updateProfile(user);
        log.info("Update user profile success: {}", user.getId());
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        log.info("Update user password: {}", userId);
        userService.updatePassword(userId, oldPassword, newPassword);
        log.info("Update user password success: {}", userId);
    }

    public List<Long> getUserRoles(Long userId) {
        log.info("Get user roles: {}", userId);
        return roleService.getRoleIdsByUserId(userId);
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        log.info("Update user roles: {}, roleIds: {}", userId, roleIds);
        roleService.updateUserRoles(userId, roleIds);
        log.info("Update user roles success: {}", userId);
    }
}
