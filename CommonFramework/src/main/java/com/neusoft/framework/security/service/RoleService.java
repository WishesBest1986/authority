package com.neusoft.framework.security.service;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.dao.RoleDao;
import com.neusoft.framework.security.entity.Role;
import com.neusoft.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    public void save(Role role) {
        roleDao.save(role);
    }

    public void delete(Long id) {
        List<User> users = userService.getAll();
        for (User user : users) {
            for (Role role : user.getRoles()) {
                if (role.getId().longValue() == id.longValue()) {
                    user.getRoles().remove(role);
                    userService.save(user);
                    break;
                }
            }
        }
        roleDao.delete(id);
    }

    public Role get(Long id) {
        return roleDao.get(id);
    }

    public Page<Role> findPage(final Page<Role> page, final List<PropertyFilter> filters) {
        return roleDao.findPage(page, filters);
    }

    public List<Role> getAll() {
        return roleDao.getAll();
    }
}
