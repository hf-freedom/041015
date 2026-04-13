package com.example.logic;

import com.example.entity.Menu;
import com.example.entity.User;
import com.example.service.MenuService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthLogic {

    private static final Logger log = LoggerFactory.getLogger(AuthLogic.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    public Map<String, Object> login(String username, String password) {
        log.info("[Auth] 用户登录开始, username: {}", username);
        try {
            Map<String, Object> result = userService.login(username, password);
            if (result != null) {
                log.info("[Auth] 用户登录成功, username: {}", username);
            } else {
                log.warn("[Auth] 用户登录失败, username: {}", username);
            }
            return result;
        } catch (Exception e) {
            log.error("[Auth] 用户登录异常, username: {}", username, e);
            throw e;
        }
    }

    public Map<String, Object> getUserInfoAndMenus(Long userId) {
        log.info("[Auth] 获取用户信息和菜单开始, userId: {}", userId);
        try {
            User user = userService.getById(userId);
            List<Menu> menus = menuService.getMenusByUserId(userId);
            Map<String, Object> result = new HashMap<>();
            result.put("user", user);
            result.put("menus", menus);
            log.info("[Auth] 获取用户信息和菜单成功, userId: {}", userId);
            return result;
        } catch (Exception e) {
            log.error("[Auth] 获取用户信息和菜单异常, userId: {}", userId, e);
            throw e;
        }
    }

    public Long getUserIdFromToken(String token) {
        return userService.getUserIdFromToken(token);
    }
}
