package com.example.controller;

import com.example.annotation.RequirePermission;
import com.example.common.Result;
import com.example.entity.Menu;
import com.example.logic.MenuLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuLogic menuLogic;

    @GetMapping("/list")
    @RequirePermission("/api/menu/list")
    public Result<List<Menu>> list() {
        return Result.success(menuLogic.listMenus());
    }

    @GetMapping("/tree")
    public Result<List<Map<String, Object>>> tree() {
        return Result.success(menuLogic.getMenuTree());
    }

    @GetMapping("/user")
    public Result<List<Menu>> userMenus(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(menuLogic.getMenusByUserId(userId));
    }

    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuLogic.getMenuById(id));
    }

    @PostMapping("/add")
    @RequirePermission("/api/menu/add")
    public Result<Void> add(@RequestBody Menu menu) {
        menuLogic.addMenu(menu);
        return Result.success();
    }

    @PutMapping("/update")
    @RequirePermission("/api/menu/update")
    public Result<Void> update(@RequestBody Menu menu) {
        menuLogic.updateMenu(menu);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermission("/api/menu/delete")
    public Result<Void> delete(@PathVariable Long id) {
        menuLogic.deleteMenu(id);
        return Result.success();
    }
}
