package com.example.logic;

import com.example.entity.ApiPermission;
import com.example.service.ApiPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ApiPermissionLogic {

    @Autowired
    private ApiPermissionService apiPermissionService;

    public List<ApiPermission> list() {
        log.info("List all api permissions");
        return apiPermissionService.list();
    }

    public ApiPermission getById(Long id) {
        log.info("Get api permission by id: {}", id);
        return apiPermissionService.getById(id);
    }

    public void add(ApiPermission api) {
        log.info("Add api permission: {}", api.getName());
        apiPermissionService.add(api);
        log.info("Add api permission success: {}", api.getId());
    }

    public void update(ApiPermission api) {
        log.info("Update api permission: {}", api.getId());
        apiPermissionService.update(api);
        log.info("Update api permission success: {}", api.getId());
    }

    public void delete(Long id) {
        log.info("Delete api permission: {}", id);
        apiPermissionService.delete(id);
        log.info("Delete api permission success: {}", id);
    }

    public Set<String> getApiUrlsByUserId(Long userId) {
        log.info("Get api urls by user id: {}", userId);
        return apiPermissionService.getApiUrlsByUserId(userId);
    }
}
