package com.neusoft.framework.security.service;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.dao.ResourceDao;
import com.neusoft.framework.security.entity.Authority;
import com.neusoft.framework.security.entity.Resource;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
@Service
public class ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private AuthorityService authorityService;

    public void save(Resource resource) {
        resourceDao.save(resource);
    }

    public void delete(Long id) {
        List<Authority> authorities = authorityService.getAll();
        for (Authority authority : authorities) {
            for (Resource resource : authority.getResources()) {
                if (resource.getId().longValue() == id.longValue()) {
                    authority.getResources().remove(resource);
                    authorityService.save(authority);
                    break;
                }
            }
        }
        resourceDao.delete(id);
    }

    public void updateByMenuId(Long menuId) {
        String hql = "from Resource as r where r.menu.id = ?";
        Resource resource = resourceDao.findUnique(hql, menuId);
        if (resource != null) {
            resource.setMenu(null);
            resourceDao.save(resource);
        }
    }

    public Resource get(Long id) {
        return resourceDao.get(id);
    }

    public Page<Resource> findPage(final Page<Resource> page, final List<PropertyFilter> filters) {
        return resourceDao.findPage(page, filters);
    }

    public List<Resource> getAll() {
        return resourceDao.getAll();
    }

    public List<Resource> getAuthorizedResource(Long userId) {
        String sql = "select re.id, re.name, re.source, re.menu from sec_user u " +
                "left outer join sec_role_user ru on u.id = ru.user_id " +
                "left outer join sec_role r on ru.role_id = r.id " +
                "left outer join sec_role_authority ra on r.id = ra.role_id " +
                "left outer join sec_authority a on ra.authority_id = a.id " +
                "left outer join sec_authority_resource ar on a.id = ar.authority_id " +
                "left outer join sec_resource re on ar.resource_id = re.id " +
                "where u.id = ? and re.menu is not null " +
                "union all " +
                "select re.id, re.name, re.source, re.menu from sec_resource re " +
                "where re.menu is not null and not exists(select ar.authority_id from sec_authority_resource ar where ar.resource_id = re.id)";
        SQLQuery query = resourceDao.createSQLQuery(sql, userId);
        query.addEntity(Resource.class);
        return query.list();
    }
}
