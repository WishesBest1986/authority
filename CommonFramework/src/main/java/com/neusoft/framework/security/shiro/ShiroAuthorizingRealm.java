package com.neusoft.framework.security.shiro;

import com.neusoft.framework.security.entity.User;
import com.neusoft.framework.security.service.UserService;
import com.neusoft.framework.utils.EncodeUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by admin on 2015/4/8.
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(ShiroAuthorizingRealm.class);

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 构造函数，设置安全的初始化信息
     */
    public ShiroAuthorizingRealm() {
        super();
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }


    /**
     * 获取当前认证实体的授权信息（授权包括：角色、权限）
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前登陆的用户名
        ShiroPrincipal subject = (ShiroPrincipal)super.getAvailablePrincipal(principals);
        String username = subject.getUsername();
        Long userId = subject.getId();
        logger.info("用户【" + username + "】授权开始……");
        try {
            if (!subject.isAuthorized()) {
                //根据用户名称，获取该用户所有的权限列表
                List<String> authorities = userService.getAuthoritiesName(userId);
                List<String> roles = userService.getRolesName(userId);
                subject.setAuthorities(authorities);
                subject.setRoles(roles);
                subject.setAuthorized(true);
                logger.info("用户【" + username + "】授权初始化成功……");
                logger.info("用户【" + username + "】角色列表为：" + subject.getRoles());
                logger.info("用户【" + username + "】权限列表为：" + subject.getAuthorities());
            } else {
                logger.info("用户【" + username + "】已授权……");
            }
        } catch (RuntimeException e) {
            throw new AuthorizationException("用户【" + username + "】授权失败");
        }

        // 给当前用户设置权限
        info.addStringPermissions(subject.getAuthorities());
        info.addRoles(subject.getRoles());
        return info;
    }

    /**
     * 根据认证方式（如表单）获取用户名称、密码
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();
        if (username == null) {
            logger.warn("用户名不能为空");
            throw new AccountException("用户名不能为空");
        }

        User user = null;
        try {
            user = userService.findUserByName(username);
        } catch (Exception ex) {
            logger.warn("获取用户失败\n" + ex.getMessage());
        }

        if (user == null) {
            logger.warn("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getEnabled() == null || "2".equals(user.getEnabled())) {
            logger.warn("用户被禁止使用");
            throw new UnknownAccountException("用户被禁止使用");
        }
        logger.info("用户【" + username + "】登陆成功");
        byte[] salt = EncodeUtils.hexDecode(user.getSalt());
        ShiroPrincipal subject = new ShiroPrincipal(user);
        List<String> authorities = userService.getAuthoritiesName(user.getId());
        List<String> roles = userService.getRolesName(user.getId());
        subject.setAuthorities(authorities);
        subject.setRoles(roles);
        subject.setAuthorized(true);
        return new SimpleAuthenticationInfo(subject, user.getPassword(), ByteSource.Util.bytes(salt), getName());
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
        matcher.setHashIterations(UserService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }
}
