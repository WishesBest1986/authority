package com.neusoft.framework.security.service;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.dao.UserDao;
import com.neusoft.framework.security.entity.Org;
import com.neusoft.framework.security.entity.User;
import com.neusoft.framework.utils.Digests;
import com.neusoft.framework.utils.EncodeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
@Service
public class UserService {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;

    public void save(User user) {
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            encryptPassword(user);
        }
        userDao.save(user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }

    public User get(Long id) {
        return userDao.get(id);
    }

    public User findUserByName(String username) {
        return userDao.findUniqueBy("username", username);
    }

    public boolean isUserNameUnique(String newUsername, String oldUsername) {
        return userDao.isPropertyUnique("username", newUsername, oldUsername);
    }

    public Page<User> findPage(final Page<User> page, final List<PropertyFilter> filters) {
        return userDao.findPage(page, filters);
    }

    public Page<User> searchUser(final Page<User> page, Long orgId) {
        Org org = new Org(orgId);

        String hql = "from User user where user.org = ?";
        return userDao.findPage(page, hql, org);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public List<User> getByOrg(Long orgId) {
        if (orgId == null || orgId == Org.ROOT_ORG_ID) {
            return userDao.getAll();
        }
        return userDao.find("from User user where user.org = ?", new Org(orgId));
    }

    public List<String> getAuthoritiesName(Long userId) {
        String sql = "select a.name from sec_user u " +
                "left outer join sec_role_user ru on u.id = ru.user_id " +
                "left outer join sec_role r on ru.role_id = r.id " +
                "left outer join sec_role_authority ra on r.id = ra.role_id " +
                "left outer join sec_authority a on ra.authority_id = a.id " +
                "where u.id = ?";
        SQLQuery query = userDao.createSQLQuery(sql, userId);
        return query.list();
    }

    public List<String> getRolesName(Long userId) {
        String sql = "select r.name from sec_user u " +
                "left outer join sec_role_user ru on u.id = ru.user_id " +
                "left outer join sec_role r on ru.role_id = r.id " +
                "where u.id = ?";
        SQLQuery query = userDao.createSQLQuery(sql);
        return query.list();
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void encryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(EncodeUtils.hexEncode(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(EncodeUtils.hexEncode(hashPassword));
    }
}
