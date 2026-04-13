package com.example.logic;

import com.example.entity.Organization;
import com.example.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrgLogic {

    @Autowired
    private OrgService orgService;

    public List<Organization> list() {
        log.info("List all organizations");
        return orgService.list();
    }

    public List<Map<String, Object>> getTree() {
        log.info("Get organization tree");
        return orgService.getTree();
    }

    public Organization getById(Long id) {
        log.info("Get organization by id: {}", id);
        return orgService.getById(id);
    }

    public void add(Organization org) {
        log.info("Add organization: {}", org.getName());
        orgService.add(org);
        log.info("Add organization success: {}", org.getId());
    }

    public void update(Organization org) {
        log.info("Update organization: {}", org.getId());
        orgService.update(org);
        log.info("Update organization success: {}", org.getId());
    }

    public void delete(Long id) {
        log.info("Delete organization: {}", id);
        orgService.delete(id);
        log.info("Delete organization success: {}", id);
    }
}
