package com.example.logic;

import com.example.entity.User;
import com.example.service.MenuService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthLogic {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    public Map<String, Object> login(String username, String password) {
        log.info("Auth login attempt: {}", username);
        Map<String, Object> result = userService.login(username, password);
        if (result != null) {
            log.info("Auth login success: {}", username);
        } else {
            log.warn("Auth login failed: {}", username);
        }
        return result;
    }

    public void logout() {
        log.info("User logout");
    }

    public Map<String, Object> getUserInfo(Long userId) {
        log.info("Get user info: {}", userId);
        User user = userService.getById(userId);
        List<Long> roleIds = menuService.getMenusByUserId(userId).stream()
                .map(m -> m.getId())
                .collect(java.util.stream.Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("menus", menuService.getMenusByUserId(userId));
        log.info("Get user info success: {}", userId);
        return result;
    }
}
