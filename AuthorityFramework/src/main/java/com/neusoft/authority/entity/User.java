package com.neusoft.authority.entity;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/3/25.
 */
public class User implements Serializable {
    private Long id;
    private Long organizationId;
    private String username;
    private String password;
    private String salt;
    private List<Long> roleIds;
    private Boolean locked = Boolean.FALSE;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRoleIdsStr() {
        if ( CollectionUtils.isEmpty(roleIds) ) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Long roleId : roleIds) {
            stringBuilder.append(roleId + ",");
        }
        return stringBuilder.toString();
    }

    public void setRoleIdsStr(String roleIdsStr) {
        if ( StringUtils.isEmpty(roleIdsStr) ) {
            return;
        }

        String[] roleIdStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdStrs) {
            if ( StringUtils.isEmpty(roleIdStr) ) {
                continue;
            }
            getRoleIds().add(Long.valueOf(roleIdStr));
        }
    }
}
