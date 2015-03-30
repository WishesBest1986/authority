package com.neusoft.authority.service;

import com.neusoft.authority.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2015/3/26.
 */
public interface ResourceService {
    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    public List<Resource> findAll();
    public Resource findOne(Long resourceId);

    Set<String> findPermissions(Set<Long> resourceIds);
    List<Resource> findMenus(Set<String> permissions);
}
