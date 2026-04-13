package com.example.mapper;

import com.example.cache.LocalCache;
import com.example.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuMapper {

    @Autowired
    private LocalCache localCache;

    public void insert(Menu menu) {
        localCache.saveMenu(menu);
    }

    public void update(Menu menu) {
        localCache.saveMenu(menu);
    }

    public void deleteById(Long id) {
        localCache.deleteMenu(id);
    }

    public Menu selectById(Long id) {
        return localCache.getMenuById(id);
    }

    public List<Menu> selectAll() {
        return localCache.getAllMenus();
    }

    public void insertRoleMenu(Long roleId, Long menuId) {
        localCache.saveRoleMenu(roleId, menuId);
    }

    public void deleteRoleMenu(Long roleId, Long menuId) {
        localCache.deleteRoleMenu(roleId, menuId);
    }

    public List<Long> selectMenuIdsByRoleId(Long roleId) {
        return localCache.getMenuIdsByRoleId(roleId);
    }

    public List<Long> selectRoleIdsByMenuId(Long menuId) {
        return localCache.getRoleIdsByMenuId(menuId);
    }

    public List<Menu> selectMenusByUserId(Long userId) {
        return localCache.getMenusByUserId(userId);
    }

    public void clearRoleMenus(Long roleId) {
        localCache.clearRoleMenus(roleId);
    }
}
