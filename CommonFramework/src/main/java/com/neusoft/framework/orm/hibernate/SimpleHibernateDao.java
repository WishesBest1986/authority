package com.neusoft.framework.orm.hibernate;

import org.apache.shiro.session.mgt.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 *
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 *
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 *
 * Created by admin on 2015/3/27.
 */
public class SimpleHibernateDao<T, PK extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected SessionFactory sessionFactory;
}
