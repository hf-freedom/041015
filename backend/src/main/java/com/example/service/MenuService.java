package com.example.service;

import com.example.cache.LocalCache;
import com.example.entity.Menu;
import com.example.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LocalCache localCache;

    public List<Menu> list() {
        return menuMapper.selectAll();
    }

    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    public void add(Menu menu) {
        menu.setId(localCache.generateMenuId());
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.insert(menu);
    }

    public void update(Menu menu) {
        Menu existing = menuMapper.selectById(menu.getId());
        if (existing != null) {
            menu.setCreateTime(existing.getCreateTime());
            menu.setUpdateTime(LocalDateTime.now());
            menuMapper.update(menu);
        }
    }

    public void delete(Long id) {
        menuMapper.deleteById(id);
    }

    public void bindMenus(Long roleId, List<Long> menuIds) {
        menuMapper.clearRoleMenus(roleId);
        for (Long menuId : menuIds) {
            menuMapper.insertRoleMenu(roleId, menuId);
        }
    }

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return menuMapper.selectMenuIdsByRoleId(roleId);
    }

    public List<Menu> getMenusByUserId(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    public List<Map<String, Object>> getTree() {
        List<Menu> all = menuMapper.selectAll();
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
