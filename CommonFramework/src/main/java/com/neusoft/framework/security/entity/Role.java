package com.neusoft.framework.security.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/7.
 */
@Entity
@Table(name = "SEC_ROLE")
public class Role extends SecurityEntity {
    private static final long serialVersionUID = 2041148498753846675L;

    private String name;

    private String description;

    private String selected;

    private List<Authority> authorities = new ArrayList<Authority>();

    private List<User> users = new ArrayList<User>();

    @Column(name = "name", unique = true, nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_ROLE_AUTHORITY", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @ManyToMany(mappedBy = "roles")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
