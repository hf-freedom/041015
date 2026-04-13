package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.ApiPermission;
import com.example.service.ApiPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class ApiPermissionController {

    @Autowired
    private ApiPermissionService apiPermissionService;

    @GetMapping("/list")
    @RequirePermission("/api/permission/list")
    public Result<List<ApiPermission>> list() {
        return Result.success(apiPermissionService.list());
    }

    @GetMapping("/{id}")
    public Result<ApiPermission> getById(@PathVariable Long id) {
        return Result.success(apiPermissionService.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/permission/add")
    public Result<Void> add(@RequestBody ApiPermission api) {
        apiPermissionService.add(api);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/permission/update")
    public Result<Void> update(@RequestBody ApiPermission api) {
        apiPermissionService.update(api);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/permission/delete")
    public Result<Void> delete(@PathVariable Long id) {
        apiPermissionService.delete(id);
        return Result.success();
    }
}
