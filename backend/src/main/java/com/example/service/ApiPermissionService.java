package com.example.service;

import com.example.entity.ApiPermission;
import com.example.mapper.ApiPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ApiPermissionService {

    @Autowired
    private ApiPermissionMapper apiPermissionMapper;

    public List<ApiPermission> list() {
        return apiPermissionMapper.selectAll();
    }

    public ApiPermission getById(Long id) {
        return apiPermissionMapper.selectById(id);
    }

    public void add(ApiPermission api) {
        api.setCreateTime(LocalDateTime.now());
        api.setUpdateTime(LocalDateTime.now());
        apiPermissionMapper.insert(api);
    }

    public void update(ApiPermission api) {
        ApiPermission existing = apiPermissionMapper.selectById(api.getId());
        if (existing != null) {
            api.setCreateTime(existing.getCreateTime());
            api.setUpdateTime(LocalDateTime.now());
            apiPermissionMapper.update(api);
        }
    }

    public void delete(Long id) {
        apiPermissionMapper.deleteById(id);
    }

    public Set<String> getApiUrlsByUserId(Long userId) {
        return apiPermissionMapper.selectApiUrlsByUserId(userId);
    }
}
