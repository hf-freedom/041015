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

    private static final Logger logger = LoggerFactory.getLogger(AuthLogic.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    public Map<String, Object> getAuthInfo(Long userId) {
        logger.debug("Getting auth info for user: {}", userId);
        User user = userService.getById(userId);
        List<Menu> menus = menuService.getMenusByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("menus", menus);
        return result;
    }
}
