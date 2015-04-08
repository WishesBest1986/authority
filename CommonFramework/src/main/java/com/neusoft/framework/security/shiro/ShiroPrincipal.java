package com.neusoft.framework.security.shiro;

import com.neusoft.framework.security.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证主体
 *
 * Created by admin on 2015/4/8.
 */
public class ShiroPrincipal implements Serializable {
    private User user;
    private List<String> authorities = new ArrayList<String>();
    private List<String> roles = new ArrayList<String>();
    //是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
    //这里会导致修改权限时，需要重新登录方可有效
    private boolean isAuthorized = false;

    public ShiroPrincipal(User user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public Long getId() {
        return this.user.getId();
    }

    /**
     * <shiro:principal/>标签显示中文名称
     */
    @Override
    public String toString() {
        return this.user.getFullname();
    }
}
