package com.neusoft.framework.security.service;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.dao.AuthorityDao;
import com.neusoft.framework.security.entity.Authority;
import com.neusoft.framework.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
@Service
public class AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private RoleService roleService;

    public void save(Authority authority) {
        authorityDao.save(authority);
    }

    public void delete(Long id) {
        List<Role> roles = roleService.getAll();
        for (Role role : roles) {
            for (Authority authority : role.getAuthorities()) {
                if (authority.getId().longValue() == id.longValue()) {
                    role.getAuthorities().remove(authority);
                    roleService.save(role);
                    break;
                }
            }
        }
        authorityDao.delete(id);
    }

    public Authority get(Long id) {
        return authorityDao.get(id);
    }

    public Page<Authority> findPage(final Page<Authority> page, final List<PropertyFilter> filters) {
        return authorityDao.findPage(page, filters);
    }

    public List<Authority> getAll() {
        return authorityDao.getAll();
    }
}
