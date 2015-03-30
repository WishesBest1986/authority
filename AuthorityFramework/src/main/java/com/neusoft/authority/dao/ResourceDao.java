package com.neusoft.authority.dao;

import com.neusoft.authority.entity.Resource;

import java.util.List;

/**
 * Created by admin on 2015/3/26.
 */
public interface ResourceDao {
    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    public List<Resource> findAll();
    public Resource findOne(Long resourceId);
}
