package com.example.service;

import com.example.entity.Organization;
import com.example.mapper.OrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrgService {

    @Autowired
    private OrgMapper orgMapper;

    public List<Organization> list() {
        return orgMapper.selectAll();
    }

    public Organization getById(Long id) {
        return orgMapper.selectById(id);
    }

    public void add(Organization org) {
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
}
