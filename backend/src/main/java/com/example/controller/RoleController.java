package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.Role;
import com.example.logic.RoleLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleLogic roleLogic;

    @GetMapping("/list")
    @RequirePermission("/api/role/list")
    public Result<List<Role>> list() {
        return Result.success(roleLogic.list());
    }

    @GetMapping("/{id}")
    public Result<Role> getById(@PathVariable Long id) {
        return Result.success(roleLogic.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/role/add")
    public Result<Void> add(@RequestBody Role role) {
        roleLogic.add(role);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/role/update")
    public Result<Void> update(@RequestBody Role role) {
        roleLogic.update(role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/role/delete")
    public Result<Void> delete(@PathVariable Long id) {
        roleLogic.delete(id);
        return Result.success();
    }

    @GetMapping("/menus/{roleId}")
    public Result<List<Long>> getRoleMenus(@PathVariable Long roleId) {
        return Result.success(roleLogic.getRoleMenus(roleId));
    }

    @PutMapping("/menus/{roleId}")
    public Result<Void> updateRoleMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        roleLogic.updateRoleMenus(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/users/{roleId}")
    public Result<List<Long>> getRoleUsers(@PathVariable Long roleId) {
        return Result.success(roleLogic.getRoleUsers(roleId));
    }

    @PostMapping("/bindUsers/{roleId}")
    public Result<Void> bindUsers(@PathVariable Long roleId, @RequestBody List<Long> userIds) {
        roleLogic.bindUsers(roleId, userIds);
        return Result.success();
    }

    @PostMapping("/unbindUsers/{roleId}")
    public Result<Void> unbindUsers(@PathVariable Long roleId, @RequestBody List<Long> userIds) {
        roleLogic.unbindUsers(roleId, userIds);
        return Result.success();
    }
}
