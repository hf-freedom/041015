package com.example.controller;

import com.example.common.Result;
import com.example.logic.AuthLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthLogic authLogic;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Map<String, Object> result = authLogic.login(username, password);
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
        return Result.success(authLogic.getUserInfoAndMenus(userId));
    }
}
