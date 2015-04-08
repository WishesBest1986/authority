package com.neusoft.framework.security.service;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.dao.OrgDao;
import com.neusoft.framework.security.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
@Service
public class OrgService {
    @Autowired
    private OrgDao orgDao;

    public void save(Org org) {
        orgDao.save(org);
    }

    public void delete(Long id) {
        orgDao.delete(id);
    }

    public Org get(Long id) {
        return orgDao.get(id);
    }

    public Page<Org> findPage(final Page<Org> page, final List<PropertyFilter> filters) {
        return orgDao.findPage(page, filters);
    }

    public List<Org> getAll() {
        return orgDao.getAll();
    }

    public List<Org> getByParent(Long parentId) {
        if (parentId == null || parentId == Org.ROOT_ORG_ID) {
            return getAll();
        }
        return orgDao.find("from Org org where org.parentOrg = ?", new Org(parentId));
    }
}
