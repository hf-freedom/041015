package com.example.logic;

import com.example.cache.LocalCache;
import com.example.entity.ApiPermission;
import com.example.service.ApiPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ApiPermissionLogic {

    private static final Logger logger = LoggerFactory.getLogger(ApiPermissionLogic.class);

    @Autowired
    private ApiPermissionService apiPermissionService;

    @Autowired
    private LocalCache localCache;

    public List<ApiPermission> listApis() {
        logger.debug("Fetching all api permissions");
        return apiPermissionService.list();
    }

    public ApiPermission getApiById(Long id) {
        logger.debug("Fetching api permission by id: {}", id);
        return apiPermissionService.getById(id);
    }

    public void addApi(ApiPermission api) {
        logger.info("Adding new api permission: {}", api.getName());
        api.setId(localCache.generateApiId());
        apiPermissionService.add(api);
        logger.info("Api permission added successfully: {} (ID: {})", api.getName(), api.getId());
    }

    public void updateApi(ApiPermission api) {
        logger.info("Updating api permission: {}", api.getId());
        apiPermissionService.update(api);
        logger.info("Api permission updated successfully: {}", api.getId());
    }

    public void deleteApi(Long id) {
        logger.info("Deleting api permission: {}", id);
        apiPermissionService.delete(id);
        logger.info("Api permission deleted successfully: {}", id);
    }

    public Set<String> getApiUrlsByUserId(Long userId) {
        logger.debug("Getting api urls for user: {}", userId);
        return apiPermissionService.getApiUrlsByUserId(userId);
    }
}
