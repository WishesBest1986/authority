package com.neusoft.framework.security.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/7.
 */
@Entity
@Table(name = "SEC_ORG")
public class Org extends SecurityEntity {
    public static final Long ROOT_ORG_ID = 0L;

    private Org parentOrg;

    private String name;

    private String active;

    private String fullname;

    private String description;

    private List<User> users = new ArrayList<User>();

    private List<Org> orgs = new ArrayList<Org>();

    public Org() {

    }

    public Org(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "parentOrg", nullable = true)
    public Org getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Org parentOrg) {
        this.parentOrg = parentOrg;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "active")
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Column(name = "fullname", length = 200)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "parentOrg", cascade = CascadeType.ALL)
    public List<Org> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Org> orgs) {
        this.orgs = orgs;
    }
}
