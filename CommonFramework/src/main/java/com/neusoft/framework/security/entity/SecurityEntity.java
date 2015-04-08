package com.neusoft.framework.security.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 安全实体抽象类
 *
 * Created by admin on 2015/4/7.
 */
@MappedSuperclass
public abstract class SecurityEntity implements Serializable {
    protected Long id;

    /**
     * 安全实体的主键生成策略为序列，序列名称为SEC_SEQUENCE
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
