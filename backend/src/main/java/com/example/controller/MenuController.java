package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.Menu;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @RequirePermission("/api/menu/list")
    public Result<List<Menu>> list() {
        return Result.success(menuService.list());
    }

    @GetMapping("/tree")
    public Result<List<Map<String, Object>>> tree() {
        return Result.success(menuService.getTree());
    }

    @GetMapping("/user")
    public Result<List<Menu>> userMenus(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(menuService.getMenusByUserId(userId));
    }

    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/menu/add")
    public Result<Void> add(@RequestBody Menu menu) {
        menuService.add(menu);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/menu/update")
    public Result<Void> update(@RequestBody Menu menu) {
        menuService.update(menu);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/menu/delete")
    public Result<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return Result.success();
    }
}
