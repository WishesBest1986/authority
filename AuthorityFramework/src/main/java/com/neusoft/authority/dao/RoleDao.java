package com.neusoft.authority.dao;

import com.neusoft.authority.entity.Role;

import java.util.List;

/**
 * Created by admin on 2015/3/25.
 */
public interface RoleDao {
    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public List<Role> findAll();
    public Role findOne(Long roleId);
}
