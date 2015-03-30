package com.neusoft.authority.service;

import com.neusoft.authority.entity.Organization;

import java.util.List;

/**
 * Created by admin on 2015/3/26.
 */
public interface OrganizationService {
    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    public List<Organization> findAll();
    public List<Organization> findAllWithExclude(Organization organization);
    public Organization findOne(Long organizationId);

    public void move(Organization source, Organization target);
}
