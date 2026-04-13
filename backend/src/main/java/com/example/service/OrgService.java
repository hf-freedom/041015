package com.example.service;

import com.example.cache.LocalCache;
import com.example.entity.Organization;
import com.example.mapper.OrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrgService {

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private LocalCache localCache;

    public List<Organization> list() {
        return orgMapper.selectAll();
    }

    public Organization getById(Long id) {
        return orgMapper.selectById(id);
    }

    public void add(Organization org) {
        org.setId(localCache.generateOrgId());
        org.setCreateTime(LocalDateTime.now());
        org.setUpdateTime(LocalDateTime.now());
        orgMapper.insert(org);
    }

    public void update(Organization org) {
        Organization existing = orgMapper.selectById(org.getId());
        if (existing != null) {
            org.setCreateTime(existing.getCreateTime());
            org.setUpdateTime(LocalDateTime.now());
            orgMapper.update(org);
        }
    }

    public void delete(Long id) {
        orgMapper.deleteById(id);
    }

    public List<Map<String, Object>> getTree() {
        List<Organization> all = orgMapper.selectAll();
        return buildTree(all, 0L);
    }

    private List<Map<String, Object>> buildTree(List<Organization> all, Long parentId) {
        List<Map<String, Object>> tree = new ArrayList<>();
        for (Organization org : all) {
            if (parentId.equals(org.getParentId())) {
                Map<String, Object> node = new java.util.HashMap<>();
                node.put("id", org.getId());
                node.put("name", org.getName());
                node.put("code", org.getCode());
                node.put("parentId", org.getParentId());
                node.put("sort", org.getSort());
                node.put("status", org.getStatus());
                node.put("createTime", org.getCreateTime());
                node.put("updateTime", org.getUpdateTime());
                node.put("children", buildTree(all, org.getId()));
                tree.add(node);
            }
        }
        return tree;
    }
}
