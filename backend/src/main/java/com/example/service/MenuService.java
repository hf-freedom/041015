package com.example.service;

import com.example.entity.Menu;
import com.example.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> list() {
        return menuMapper.selectAll();
    }

    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    public void add(Menu menu) {
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
}
