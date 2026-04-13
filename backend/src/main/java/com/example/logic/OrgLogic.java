package com.example.logic;

import com.example.cache.LocalCache;
import com.example.entity.Organization;
import com.example.service.OrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrgLogic {

    private static final Logger logger = LoggerFactory.getLogger(OrgLogic.class);

    @Autowired
    private OrgService orgService;

    @Autowired
    private LocalCache localCache;

    public List<Organization> listOrgs() {
        logger.debug("Fetching all organizations");
        return orgService.list();
    }

    public Organization getOrgById(Long id) {
        logger.debug("Fetching organization by id: {}", id);
        return orgService.getById(id);
    }

    public void addOrg(Organization org) {
        logger.info("Adding new organization: {}", org.getName());
        org.setId(localCache.generateOrgId());
        orgService.add(org);
        logger.info("Organization added successfully: {} (ID: {})", org.getName(), org.getId());
    }

    public void updateOrg(Organization org) {
        logger.info("Updating organization: {}", org.getId());
        orgService.update(org);
        logger.info("Organization updated successfully: {}", org.getId());
    }

    public void deleteOrg(Long id) {
        logger.info("Deleting organization: {}", id);
        orgService.delete(id);
        logger.info("Organization deleted successfully: {}", id);
    }

    public List<Map<String, Object>> getOrgTree() {
        logger.debug("Building organization tree");
        List<Organization> all = orgService.list();
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
