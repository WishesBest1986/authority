package com.neusoft.framework.security.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/7.
 */
@Entity
@Table(name = "SEC_AUTHORITY")
public class Authority extends SecurityEntity {
    private String name;

    private String description;

    private Integer selected;

    private List<Resource> resources = new ArrayList<Resource>();

    private List<Role> roles = new ArrayList<Role>();

    private List<User> users = new ArrayList<User>();

    public Authority() {

    }

    /**
     * 构造函数，参数为主键ID
     * @param id
     */
    public Authority(Long id) {
        this.id = id;
    }

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
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_AUTHORITY_RESOURCE", joinColumns = {@JoinColumn(name = "AUTHORITY_ID")}, inverseJoinColumns = {@JoinColumn(name = "RESOURCE_ID")})
    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @ManyToMany(mappedBy = "authorities")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "authorities")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
