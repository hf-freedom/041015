package com.example.mapper;

import com.example.cache.LocalCache;
import com.example.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgMapper {

    @Autowired
    private LocalCache localCache;

    public void insert(Organization org) {
        localCache.saveOrg(org);
    }

    public void update(Organization org) {
        localCache.saveOrg(org);
    }

    public void deleteById(Long id) {
        localCache.deleteOrg(id);
    }

    public Organization selectById(Long id) {
        return localCache.getOrgById(id);
    }

    public List<Organization> selectAll() {
        return localCache.getAllOrgs();
    }
}
