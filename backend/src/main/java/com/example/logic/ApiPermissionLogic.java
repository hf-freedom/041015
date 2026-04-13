package com.example.logic;

import com.example.entity.ApiPermission;
import com.example.service.ApiPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiPermissionLogic {

    private static final Logger log = LoggerFactory.getLogger(ApiPermissionLogic.class);

    @Autowired
    private ApiPermissionService apiPermissionService;

    public List<ApiPermission> list() {
        log.info("[ApiPermission] 查询API权限列表开始");
        try {
            List<ApiPermission> permissions = apiPermissionService.list();
            log.info("[ApiPermission] 查询API权限列表成功, 数量: {}", permissions.size());
            return permissions;
        } catch (Exception e) {
            log.error("[ApiPermission] 查询API权限列表异常", e);
            throw e;
        }
    }

    public ApiPermission getById(Long id) {
        log.info("[ApiPermission] 查询API权限详情开始, id: {}", id);
        try {
            ApiPermission permission = apiPermissionService.getById(id);
            log.info("[ApiPermission] 查询API权限详情成功, id: {}", id);
            return permission;
        } catch (Exception e) {
            log.error("[ApiPermission] 查询API权限详情异常, id: {}", id, e);
            throw e;
        }
    }

    public void add(ApiPermission apiPermission) {
        log.info("[ApiPermission] 添加API权限开始, url: {}", apiPermission.getUrl());
        try {
            apiPermissionService.add(apiPermission);
            log.info("[ApiPermission] 添加API权限成功, url: {}", apiPermission.getUrl());
        } catch (Exception e) {
            log.error("[ApiPermission] 添加API权限异常, url: {}", apiPermission.getUrl(), e);
            throw e;
        }
    }

    public void update(ApiPermission apiPermission) {
        log.info("[ApiPermission] 更新API权限开始, id: {}", apiPermission.getId());
        try {
            apiPermissionService.update(apiPermission);
            log.info("[ApiPermission] 更新API权限成功, id: {}", apiPermission.getId());
        } catch (Exception e) {
            log.error("[ApiPermission] 更新API权限异常, id: {}", apiPermission.getId(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        log.info("[ApiPermission] 删除API权限开始, id: {}", id);
        try {
            apiPermissionService.delete(id);
            log.info("[ApiPermission] 删除API权限成功, id: {}", id);
        } catch (Exception e) {
            log.error("[ApiPermission] 删除API权限异常, id: {}", id, e);
            throw e;
        }
    }

    public java.util.Set<String> getApiUrlsByUserId(Long userId) {
        log.info("[ApiPermission] 查询用户权限开始, userId: {}", userId);
        try {
            java.util.Set<String> permissions = apiPermissionService.getApiUrlsByUserId(userId);
            log.info("[ApiPermission] 查询用户权限成功, userId: {}", userId);
            return permissions;
        } catch (Exception e) {
            log.error("[ApiPermission] 查询用户权限异常, userId: {}", userId, e);
            throw e;
        }
    }
}
