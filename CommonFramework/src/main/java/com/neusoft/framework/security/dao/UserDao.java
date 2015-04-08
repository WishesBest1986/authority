package com.neusoft.framework.security.dao;

import com.neusoft.framework.orm.hibernate.HibernateDao;
import com.neusoft.framework.security.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2015/4/7.
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {
}
