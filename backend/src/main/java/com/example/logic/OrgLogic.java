package com.example.logic;

import com.example.entity.Organization;
import com.example.service.OrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrgLogic {

    private static final Logger log = LoggerFactory.getLogger(OrgLogic.class);

    @Autowired
    private OrgService orgService;

    public List<Organization> list() {
        log.info("[Org] 查询组织机构列表开始");
        try {
            List<Organization> orgs = orgService.list();
            log.info("[Org] 查询组织机构列表成功, 数量: {}", orgs.size());
            return orgs;
        } catch (Exception e) {
            log.error("[Org] 查询组织机构列表异常", e);
            throw e;
        }
    }

    public Organization getById(Long id) {
        log.info("[Org] 查询组织机构详情开始, id: {}", id);
        try {
            Organization org = orgService.getById(id);
            log.info("[Org] 查询组织机构详情成功, id: {}", id);
            return org;
        } catch (Exception e) {
            log.error("[Org] 查询组织机构详情异常, id: {}", id, e);
            throw e;
        }
    }

    public void add(Organization org) {
        log.info("[Org] 添加组织机构开始, name: {}", org.getName());
        try {
            orgService.add(org);
            log.info("[Org] 添加组织机构成功, name: {}", org.getName());
        } catch (Exception e) {
            log.error("[Org] 添加组织机构异常, name: {}", org.getName(), e);
            throw e;
        }
    }

    public void update(Organization org) {
        log.info("[Org] 更新组织机构开始, id: {}", org.getId());
        try {
            orgService.update(org);
            log.info("[Org] 更新组织机构成功, id: {}", org.getId());
        } catch (Exception e) {
            log.error("[Org] 更新组织机构异常, id: {}", org.getId(), e);
            throw e;
        }
    }

    public void delete(Long id) {
        log.info("[Org] 删除组织机构开始, id: {}", id);
        try {
            orgService.delete(id);
            log.info("[Org] 删除组织机构成功, id: {}", id);
        } catch (Exception e) {
            log.error("[Org] 删除组织机构异常, id: {}", id, e);
            throw e;
        }
    }

    public List<Map<String, Object>> getTree() {
        log.info("[Org] 查询组织机构树开始");
        try {
            List<Map<String, Object>> tree = orgService.getTree();
            log.info("[Org] 查询组织机构树成功");
            return tree;
        } catch (Exception e) {
            log.error("[Org] 查询组织机构树异常", e);
            throw e;
        }
    }
}
