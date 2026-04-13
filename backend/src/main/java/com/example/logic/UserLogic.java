package com.example.logic;

import com.example.entity.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserLogic {

    private static final Logger log = LoggerFactory.getLogger(UserLogic.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public Map<String, Object> login(String username, String password) {
        log.info("[Login] 用户登录开始, username: {}", username);
        try {
            Map<String, Object> result = userService.login(username, password);
            if (result != null) {
                log.info("[Login] 用户登录成功, username: {}", username);
            } else {
                log.warn("[Login] 用户登录失败, username: {}", username);
            }
            return result;
        } catch (Exception e) {
            log.error("[Login] 用户登录异常, username: {}", username, e);
            throw e;
        }
    }

    public List<User> list() {
        log.info("[User] 查询用户列表开始");
        try {
            List<User> users = userService.list();
            log.info("[User] 查询用户列表成功, 数量: {}", users.size());
            return users;
        } catch (Exception e) {
            log.error("[User] 查询用户列表异常", e);
            throw e;
        }
    }

    public User getById(Long id) {
        log.info("[User] 查询用户详情开始, id: {}", id);
        try {
            User user = userService.getById(id);
            log.info("[User] 查询用户详情成功, id: {}", id);
            return user;
        } catch (Exception e) {
            log.error("[User] 查询用户详情异常, id: {}", id, e);
            throw e;
        }
    }

    public void add(User user) {
        log.info("[User] 添加用户开始, username: {}", user.getUsername());
        try {
            userService.add(user);
            log.info("[User] 添加用户成功, username: {}", user.getUsername());
        } catch (Exception e) {
            log.error("[User] 添加用户异常, username: {}", user.getUsername(), e);
            throw e;
        }
    }

    public void update(User user) {
        log.info("[User] 更新用户开始, id: {}", user.getId());
        try {
            userService.update(user);
            log.info("[User] 更新用户成功, id: {}", user.getId());
        } catch (Exception e) {
            log.error("[User] 更新用户异常, id: {}", user.getId(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        log.info("[User] 删除用户开始, id: {}", id);
        try {
            userService.delete(id);
            log.info("[User] 删除用户成功, id: {}", id);
        } catch (Exception e) {
            log.error("[User] 删除用户异常, id: {}", id, e);
            throw e;
        }
    }

    public void updateProfile(User user) {
        log.info("[User] 更新个人资料开始, userId: {}", user.getId());
        try {
            userService.updateProfile(user);
            log.info("[User] 更新个人资料成功, userId: {}", user.getId());
        } catch (Exception e) {
            log.error("[User] 更新个人资料异常, userId: {}", user.getId(), e);
            throw e;
        }
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        log.info("[User] 更新密码开始, userId: {}", userId);
        try {
            userService.updatePassword(userId, oldPassword, newPassword);
            log.info("[User] 更新密码成功, userId: {}", userId);
        } catch (Exception e) {
            log.error("[User] 更新密码异常, userId: {}", userId, e);
            throw e;
        }
    }

    public List<Long> getUserRoles(Long userId) {
        log.info("[User] 查询用户角色开始, userId: {}", userId);
        try {
            List<Long> roleIds = roleService.getRoleIdsByUserId(userId);
            log.info("[User] 查询用户角色成功, userId: {}", userId);
            return roleIds;
        } catch (Exception e) {
            log.error("[User] 查询用户角色异常, userId: {}", userId, e);
            throw e;
        }
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        log.info("[User] 更新用户角色开始, userId: {}, roleIds: {}", userId, roleIds);
        try {
            roleService.updateUserRoles(userId, roleIds);
            log.info("[User] 更新用户角色成功, userId: {}", userId);
        } catch (Exception e) {
            log.error("[User] 更新用户角色异常, userId: {}", userId, e);
            throw e;
        }
    }
}
