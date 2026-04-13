package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.ApiPermission;
import com.example.logic.ApiPermissionLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class ApiPermissionController {

    @Autowired
    private ApiPermissionLogic apiPermissionLogic;

    @GetMapping("/list")
    @RequirePermission("/api/permission/list")
    public Result<List<ApiPermission>> list() {
        return Result.success(apiPermissionLogic.list());
    }

    @GetMapping("/{id}")
    public Result<ApiPermission> getById(@PathVariable Long id) {
        return Result.success(apiPermissionLogic.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/permission/add")
    public Result<Void> add(@RequestBody ApiPermission api) {
        apiPermissionLogic.add(api);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/permission/update")
    public Result<Void> update(@RequestBody ApiPermission api) {
        apiPermissionLogic.update(api);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/permission/delete")
    public Result<Void> delete(@PathVariable Long id) {
        apiPermissionLogic.delete(id);
        return Result.success();
    }
}
