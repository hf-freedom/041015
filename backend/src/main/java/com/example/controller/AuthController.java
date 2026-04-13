package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.MenuService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Map<String, Object> result = userService.login(username, password);
        if (result == null) {
            return Result.error("Username or password incorrect");
        }
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        List<Long> roleIds = menuService.getMenusByUserId(userId).stream()
                .map(m -> m.getId())
                .collect(java.util.stream.Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("menus", menuService.getMenusByUserId(userId));
        return Result.success(result);
    }
}
