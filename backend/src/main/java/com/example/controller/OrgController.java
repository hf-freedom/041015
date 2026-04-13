package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.Organization;
import com.example.logic.OrgLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/org")
public class OrgController {

    @Autowired
    private OrgLogic orgLogic;

    @GetMapping("/list")
    @RequirePermission("/api/org/list")
    public Result<List<Organization>> list() {
        return Result.success(orgLogic.list());
    }

    @GetMapping("/tree")
    public Result<List<Map<String, Object>>> tree() {
        return Result.success(orgLogic.getTree());
    }

    @GetMapping("/{id}")
    public Result<Organization> getById(@PathVariable Long id) {
        return Result.success(orgLogic.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/org/add")
    public Result<Void> add(@RequestBody Organization org) {
        orgLogic.add(org);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/org/update")
    public Result<Void> update(@RequestBody Organization org) {
        orgLogic.update(org);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/org/delete")
    public Result<Void> delete(@PathVariable Long id) {
        orgLogic.delete(id);
        return Result.success();
    }
}
