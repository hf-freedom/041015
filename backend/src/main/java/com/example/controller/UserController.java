package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.User;
import com.example.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserLogic userLogic;

    @GetMapping("/list")
    @RequirePermission("/api/user/list")
    public Result<List<User>> list() {
        return Result.success(userLogic.list());
    }

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return Result.success(userLogic.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/user/add")
    public Result<Void> add(@RequestBody User user) {
        userLogic.add(user);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/user/update")
    public Result<Void> update(@RequestBody User user) {
        userLogic.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/user/delete")
    public Result<Void> delete(@PathVariable Long id) {
        userLogic.delete(id);
        return Result.success();
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        userLogic.updateProfile(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        userLogic.updatePassword(userId, oldPassword, newPassword);
        return Result.success();
    }

    @GetMapping("/roles/{userId}")
    public Result<List<Long>> getUserRoles(@PathVariable Long userId) {
        return Result.success(userLogic.getUserRoles(userId));
    }

    @PutMapping("/roles/{userId}")
    public Result<Void> updateUserRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userLogic.updateUserRoles(userId, roleIds);
        return Result.success();
    }
}
