package com.example.mapper;

import com.example.cache.LocalCache;
import com.example.entity.ApiPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ApiPermissionMapper {

    @Autowired
    private LocalCache localCache;

    public void insert(ApiPermission api) {
        localCache.saveApi(api);
    }

    public void update(ApiPermission api) {
        localCache.saveApi(api);
    }

    public void deleteById(Long id) {
        localCache.deleteApi(id);
    }

    public ApiPermission selectById(Long id) {
        return localCache.getApiById(id);
    }

    public List<ApiPermission> selectAll() {
        return localCache.getAllApis();
    }

    public Set<String> selectApiUrlsByUserId(Long userId) {
        return localCache.getApiUrlsByUserId(userId);
    }
}
