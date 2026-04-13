package com.example.logic;

import com.example.cache.LocalCache;
import com.example.entity.Menu;
import com.example.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuLogic {

    private static final Logger logger = LoggerFactory.getLogger(MenuLogic.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private LocalCache localCache;

    public List<Menu> listMenus() {
        logger.debug("Fetching all menus");
        return menuService.list();
    }

    public Menu getMenuById(Long id) {
        logger.debug("Fetching menu by id: {}", id);
        return menuService.getById(id);
    }

    public void addMenu(Menu menu) {
        logger.info("Adding new menu: {}", menu.getName());
        menu.setId(localCache.generateMenuId());
        menuService.add(menu);
        logger.info("Menu added successfully: {} (ID: {})", menu.getName(), menu.getId());
    }

    public void updateMenu(Menu menu) {
        logger.info("Updating menu: {}", menu.getId());
        menuService.update(menu);
        logger.info("Menu updated successfully: {}", menu.getId());
    }

    public void deleteMenu(Long id) {
        logger.info("Deleting menu: {}", id);
        menuService.delete(id);
        logger.info("Menu deleted successfully: {}", id);
    }

    public void bindMenus(Long roleId, List<Long> menuIds) {
        logger.info("Binding menus to role {}: {}", roleId, menuIds);
        menuService.bindMenus(roleId, menuIds);
        logger.info("Menus bound to role {} successfully", roleId);
    }

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        logger.debug("Getting menu ids for role: {}", roleId);
        return menuService.getMenuIdsByRoleId(roleId);
    }

    public List<Menu> getMenusByUserId(Long userId) {
        logger.debug("Getting menus for user: {}", userId);
        return menuService.getMenusByUserId(userId);
    }

    public List<Map<String, Object>> getMenuTree() {
        logger.debug("Building menu tree");
        List<Menu> all = menuService.list();
        return buildTree(all, 0L);
    }

    private List<Map<String, Object>> buildTree(List<Menu> all, Long parentId) {
        List<Map<String, Object>> tree = new ArrayList<>();
        for (Menu menu : all) {
            if (parentId.equals(menu.getParentId())) {
                Map<String, Object> node = new HashMap<>();
                node.put("id", menu.getId());
                node.put("name", menu.getName());
                node.put("path", menu.getPath());
                node.put("component", menu.getComponent());
                node.put("parentId", menu.getParentId());
                node.put("icon", menu.getIcon());
                node.put("sort", menu.getSort());
                node.put("type", menu.getType());
                node.put("status", menu.getStatus());
                node.put("createTime", menu.getCreateTime());
                node.put("updateTime", menu.getUpdateTime());
                node.put("children", buildTree(all, menu.getId()));
                tree.add(node);
            }
        }
        return tree;
    }
}
