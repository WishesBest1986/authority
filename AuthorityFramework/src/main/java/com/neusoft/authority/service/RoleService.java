package com.neusoft.authority.service;

import com.neusoft.authority.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2015/3/26.
 */
public interface RoleService {
    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public List<Role> findAll();
    public Role findOne(Long roleId);

    public Set<String> findRoles(Long... roleIds);
    public Set<String> findPermissions(Long[] roleIds);
}
