package com.example.cache;

import com.example.entity.*;
import com.example.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class LocalCache {

    private final Map<Long, User> userCache = new ConcurrentHashMap<>();
    private final Map<Long, Organization> orgCache = new ConcurrentHashMap<>();
    private final Map<Long, Role> roleCache = new ConcurrentHashMap<>();
    private final Map<Long, Menu> menuCache = new ConcurrentHashMap<>();
    private final Map<Long, ApiPermission> apiCache = new ConcurrentHashMap<>();
    private final List<UserRole> userRoleCache = Collections.synchronizedList(new ArrayList<>());
    private final List<RoleMenu> roleMenuCache = Collections.synchronizedList(new ArrayList<>());

    private final AtomicLong userIdGenerator = new AtomicLong(1);
    private final AtomicLong orgIdGenerator = new AtomicLong(1);
    private final AtomicLong roleIdGenerator = new AtomicLong(1);
    private final AtomicLong menuIdGenerator = new AtomicLong(1);
    private final AtomicLong apiIdGenerator = new AtomicLong(1);

    @Autowired
    private AdminConfig adminConfig;

    @PostConstruct
    public void init() {
        initAdminUser();
        initDefaultOrg();
        initDefaultRole();
        initDefaultMenu();
        initDefaultApi();
    }

    private void initAdminUser() {
        User admin = new User();
        admin.setId(userIdGenerator.getAndIncrement());
        admin.setUsername(adminConfig.getUsername());
        admin.setPassword(adminConfig.getPassword());
        admin.setNickname("Administrator");
        admin.setStatus(1);
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        userCache.put(admin.getId(), admin);
    }

    private void initDefaultOrg() {
        Organization org = new Organization();
        org.setId(orgIdGenerator.getAndIncrement());
        org.setName("Headquarters");
        org.setCode("HQ");
        org.setParentId(0L);
        org.setSort(1);
        org.setStatus(1);
        org.setCreateTime(LocalDateTime.now());
        org.setUpdateTime(LocalDateTime.now());
        orgCache.put(org.getId(), org);
    }

    private void initDefaultRole() {
        Role role = new Role();
        role.setId(roleIdGenerator.getAndIncrement());
        role.setName("Administrator");
        role.setCode("ADMIN");
        role.setDescription("System Administrator");
        role.setStatus(1);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleCache.put(role.getId(), role);

        UserRole userRole = new UserRole();
        userRole.setUserId(1L);
        userRole.setRoleId(role.getId());
        userRoleCache.add(userRole);
    }

    private void initDefaultMenu() {
        String[][] menus = {
            {"1", "System Management", "/system", "Layout", "0", "setting", "1", "1"},
            {"2", "User Management", "/system/user", "system/user/index", "1", "user", "1", "1"},
            {"3", "Organization Management", "/system/org", "system/org/index", "1", "tree", "2", "1"},
            {"4", "Role Management", "/system/role", "system/role/index", "1", "peoples", "3", "1"},
            {"5", "Menu Management", "/system/menu", "system/menu/index", "1", "tree-table", "4", "1"},
            {"6", "API Management", "/system/api", "system/api/index", "1", "lock", "5", "1"}
        };

        for (String[] menuData : menus) {
            Menu menu = new Menu();
            menu.setId(Long.parseLong(menuData[0]));
            menu.setName(menuData[1]);
            menu.setPath(menuData[2]);
            menu.setComponent(menuData[3]);
            menu.setParentId(Long.parseLong(menuData[4]));
            menu.setIcon(menuData[5]);
            menu.setSort(Integer.parseInt(menuData[6]));
            menu.setType(Integer.parseInt(menuData[7]));
            menu.setStatus(1);
            menu.setCreateTime(LocalDateTime.now());
            menu.setUpdateTime(LocalDateTime.now());
            menuCache.put(menu.getId(), menu);
            menuIdGenerator.set(Math.max(menuIdGenerator.get(), menu.getId() + 1));
        }

        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(1L);
        for (Long menuId : menuCache.keySet()) {
            roleMenu.setMenuId(menuId);
            roleMenuCache.add(new RoleMenu(1L, menuId));
        }
    }

    private void initDefaultApi() {
        String[][] apis = {
            {"1", "User List", "/api/user/list", "GET", "2"},
            {"2", "User Add", "/api/user/add", "POST", "2"},
            {"3", "User Update", "/api/user/update", "PUT", "2"},
            {"4", "User Delete", "/api/user/delete", "DELETE", "2"},
            {"5", "Org List", "/api/org/list", "GET", "3"},
            {"6", "Org Add", "/api/org/add", "POST", "3"},
            {"7", "Org Update", "/api/org/update", "PUT", "3"},
            {"8", "Org Delete", "/api/org/delete", "DELETE", "3"},
            {"9", "Role List", "/api/role/list", "GET", "4"},
            {"10", "Role Add", "/api/role/add", "POST", "4"},
            {"11", "Role Update", "/api/role/update", "PUT", "4"},
            {"12", "Role Delete", "/api/role/delete", "DELETE", "4"},
            {"13", "Menu List", "/api/menu/list", "GET", "5"},
            {"14", "Menu Add", "/api/menu/add", "POST", "5"},
            {"15", "Menu Update", "/api/menu/update", "PUT", "5"},
            {"16", "Menu Delete", "/api/menu/delete", "DELETE", "5"},
            {"17", "API List", "/api/permission/list", "GET", "6"},
            {"18", "API Add", "/api/permission/add", "POST", "6"},
            {"19", "API Update", "/api/permission/update", "PUT", "6"},
            {"20", "API Delete", "/api/permission/delete", "DELETE", "6"}
        };

        for (String[] apiData : apis) {
            ApiPermission api = new ApiPermission();
            api.setId(Long.parseLong(apiData[0]));
            api.setName(apiData[1]);
            api.setUrl(apiData[2]);
            api.setMethod(apiData[3]);
            api.setMenuId(Long.parseLong(apiData[4]));
            api.setCreateTime(LocalDateTime.now());
            api.setUpdateTime(LocalDateTime.now());
            apiCache.put(api.getId(), api);
            apiIdGenerator.set(Math.max(apiIdGenerator.get(), api.getId() + 1));
        }
    }

    public Long generateUserId() {
        return userIdGenerator.getAndIncrement();
    }

    public Long generateOrgId() {
        return orgIdGenerator.getAndIncrement();
    }

    public Long generateRoleId() {
        return roleIdGenerator.getAndIncrement();
    }

    public Long generateMenuId() {
        return menuIdGenerator.getAndIncrement();
    }

    public Long generateApiId() {
        return apiIdGenerator.getAndIncrement();
    }

    public void saveUser(User user) {
        userCache.put(user.getId(), user);
    }

    public User getUserById(Long id) {
        return userCache.get(id);
    }

    public User getUserByUsername(String username) {
        return userCache.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userCache.values());
    }

    public void deleteUser(Long id) {
        userCache.remove(id);
        userRoleCache.removeIf(ur -> ur.getUserId().equals(id));
    }

    public void saveOrg(Organization org) {
        orgCache.put(org.getId(), org);
    }

    public Organization getOrgById(Long id) {
        return orgCache.get(id);
    }

    public List<Organization> getAllOrgs() {
        return new ArrayList<>(orgCache.values());
    }

    public void deleteOrg(Long id) {
        orgCache.remove(id);
    }

    public void saveRole(Role role) {
        roleCache.put(role.getId(), role);
    }

    public Role getRoleById(Long id) {
        return roleCache.get(id);
    }

    public List<Role> getAllRoles() {
        return new ArrayList<>(roleCache.values());
    }

    public void deleteRole(Long id) {
        roleCache.remove(id);
        userRoleCache.removeIf(ur -> ur.getRoleId().equals(id));
        roleMenuCache.removeIf(rm -> rm.getRoleId().equals(id));
    }

    public void saveMenu(Menu menu) {
        menuCache.put(menu.getId(), menu);
    }

    public Menu getMenuById(Long id) {
        return menuCache.get(id);
    }

    public List<Menu> getAllMenus() {
        return new ArrayList<>(menuCache.values());
    }

    public void deleteMenu(Long id) {
        menuCache.remove(id);
        roleMenuCache.removeIf(rm -> rm.getMenuId().equals(id));
    }

    public void saveApi(ApiPermission api) {
        apiCache.put(api.getId(), api);
    }

    public ApiPermission getApiById(Long id) {
        return apiCache.get(id);
    }

    public List<ApiPermission> getAllApis() {
        return new ArrayList<>(apiCache.values());
    }

    public void deleteApi(Long id) {
        apiCache.remove(id);
    }

    public void saveUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleCache.add(userRole);
    }

    public void deleteUserRole(Long userId, Long roleId) {
        userRoleCache.removeIf(ur -> ur.getUserId().equals(userId) && ur.getRoleId().equals(roleId));
    }

    public List<Long> getRoleIdsByUserId(Long userId) {
        return userRoleCache.stream()
                .filter(ur -> ur.getUserId().equals(userId))
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }

    public List<Long> getUserIdsByRoleId(Long roleId) {
        return userRoleCache.stream()
                .filter(ur -> ur.getRoleId().equals(roleId))
                .map(UserRole::getUserId)
                .collect(Collectors.toList());
    }

    public void saveRoleMenu(Long roleId, Long menuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        roleMenuCache.add(roleMenu);
    }

    public void deleteRoleMenu(Long roleId, Long menuId) {
        roleMenuCache.removeIf(rm -> rm.getRoleId().equals(roleId) && rm.getMenuId().equals(menuId));
    }

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return roleMenuCache.stream()
                .filter(rm -> rm.getRoleId().equals(roleId))
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

    public List<Long> getRoleIdsByMenuId(Long menuId) {
        return roleMenuCache.stream()
                .filter(rm -> rm.getMenuId().equals(menuId))
                .map(RoleMenu::getRoleId)
                .collect(Collectors.toList());
    }

    public List<Menu> getMenusByUserId(Long userId) {
        List<Long> roleIds = getRoleIdsByUserId(userId);
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            menuIds.addAll(getMenuIdsByRoleId(roleId));
        }
        return menuIds.stream()
                .map(menuCache::get)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Menu::getSort))
                .collect(Collectors.toList());
    }

    public Set<String> getApiUrlsByUserId(Long userId) {
        List<Menu> menus = getMenusByUserId(userId);
        Set<Long> menuIds = menus.stream().map(Menu::getId).collect(Collectors.toSet());
        return apiCache.values().stream()
                .filter(api -> menuIds.contains(api.getMenuId()))
                .map(ApiPermission::getUrl)
                .collect(Collectors.toSet());
    }

    public void clearUserRoles(Long userId) {
        userRoleCache.removeIf(ur -> ur.getUserId().equals(userId));
    }

    public void clearRoleMenus(Long roleId) {
        roleMenuCache.removeIf(rm -> rm.getRoleId().equals(roleId));
    }
}
