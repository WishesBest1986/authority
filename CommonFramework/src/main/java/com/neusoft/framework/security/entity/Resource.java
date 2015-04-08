package com.neusoft.framework.security.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/4/7.
 */
@Entity
@Table(name = "SEC_RESOURCE")
public class Resource extends SecurityEntity {
    private String name;

    private String source;

    private Integer selected;

    private List<Authority> authorities = new ArrayList<Authority>();

    private Menu menu;

    public Resource() {

    }

    public Resource(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "source", length = 200)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Transient
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @ManyToMany(mappedBy = "resources", fetch = FetchType.EAGER)
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
