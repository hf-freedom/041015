package com.example.logic;

import com.example.entity.Menu;
import com.example.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MenuLogic {

    @Autowired
    private MenuService menuService;

    public List<Menu> list() {
        log.info("List all menus");
        return menuService.list();
    }

    public List<Map<String, Object>> getTree() {
        log.info("Get menu tree");
        return menuService.getTree();
    }

    public List<Menu> getMenusByUserId(Long userId) {
        log.info("Get menus by user id: {}", userId);
        return menuService.getMenusByUserId(userId);
    }

    public Menu getById(Long id) {
        log.info("Get menu by id: {}", id);
        return menuService.getById(id);
    }

    public void add(Menu menu) {
        log.info("Add menu: {}", menu.getName());
        menuService.add(menu);
        log.info("Add menu success: {}", menu.getId());
    }

    public void update(Menu menu) {
        log.info("Update menu: {}", menu.getId());
        menuService.update(menu);
        log.info("Update menu success: {}", menu.getId());
    }

    public void delete(Long id) {
        log.info("Delete menu: {}", id);
        menuService.delete(id);
        log.info("Delete menu success: {}", id);
    }
}
