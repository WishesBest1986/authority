package com.neusoft.framework.security.dao;

import com.neusoft.framework.orm.hibernate.HibernateDao;
import com.neusoft.framework.security.entity.Resource;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2015/4/8.
 */
@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {
}
