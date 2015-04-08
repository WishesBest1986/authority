package com.neusoft.framework.security.dao;

import com.neusoft.framework.orm.hibernate.HibernateDao;
import com.neusoft.framework.security.entity.Authority;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2015/4/8.
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
