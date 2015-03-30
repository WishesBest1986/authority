package com.neusoft.authority.dao;

import com.neusoft.authority.entity.Organization;

import java.util.List;

/**
 * Created by admin on 2015/3/25.
 */
public interface OrganizationDao {
    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    public List<Organization> findAll();
    public List<Organization> findAllWithExclude(Organization organization);
    public Organization findOne(Long organizationId);

    public void move(Organization source, Organization target);
}
