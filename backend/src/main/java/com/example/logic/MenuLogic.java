package com.example.logic;

import com.example.entity.Menu;
import com.example.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MenuLogic {

    private static final Logger log = LoggerFactory.getLogger(MenuLogic.class);

    @Autowired
    private MenuService menuService;

    public List<Menu> list() {
        log.info("[Menu] 查询菜单列表开始");
        try {
            List<Menu> menus = menuService.list();
            log.info("[Menu] 查询菜单列表成功, 数量: {}", menus.size());
            return menus;
        } catch (Exception e) {
            log.error("[Menu] 查询菜单列表异常", e);
            throw e;
        }
    }

    public Menu getById(Long id) {
        log.info("[Menu] 查询菜单详情开始, id: {}", id);
        try {
            Menu menu = menuService.getById(id);
            log.info("[Menu] 查询菜单详情成功, id: {}", id);
            return menu;
        } catch (Exception e) {
            log.error("[Menu] 查询菜单详情异常, id: {}", id, e);
            throw e;
        }
    }

    public void add(Menu menu) {
        log.info("[Menu] 添加菜单开始, name: {}", menu.getName());
        try {
            menuService.add(menu);
            log.info("[Menu] 添加菜单成功, name: {}", menu.getName());
        } catch (Exception e) {
            log.error("[Menu] 添加菜单异常, name: {}", menu.getName(), e);
            throw e;
        }
    }

    public void update(Menu menu) {
        log.info("[Menu] 更新菜单开始, id: {}", menu.getId());
        try {
            menuService.update(menu);
            log.info("[Menu] 更新菜单成功, id: {}", menu.getId());
        } catch (Exception e) {
            log.error("[Menu] 更新菜单异常, id: {}", menu.getId(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        log.info("[Menu] 删除菜单开始, id: {}", id);
        try {
            menuService.delete(id);
            log.info("[Menu] 删除菜单成功, id: {}", id);
        } catch (Exception e) {
            log.error("[Menu] 删除菜单异常, id: {}", id, e);
            throw e;
        }
    }

    public List<Menu> getMenusByUserId(Long userId) {
        log.info("[Menu] 查询用户菜单开始, userId: {}", userId);
        try {
            List<Menu> menus = menuService.getMenusByUserId(userId);
            log.info("[Menu] 查询用户菜单成功, userId: {}", userId);
            return menus;
        } catch (Exception e) {
            log.error("[Menu] 查询用户菜单异常, userId: {}", userId, e);
            throw e;
        }
    }



    public List<Map<String, Object>> getTree() {
        log.info("[Menu] 查询菜单树开始");
        try {
            List<Map<String, Object>> tree = menuService.getTree();
            log.info("[Menu] 查询菜单树成功");
            return tree;
        } catch (Exception e) {
            log.error("[Menu] 查询菜单树异常", e);
            throw e;
        }
    }
}
