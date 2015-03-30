package com.neusoft.authority.dao;

import com.neusoft.authority.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2015/3/26.
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Organization createOrganization(final Organization organization) {
        final String sql = "insert into sys_organization(name, parent_id, parent_ids, available) values(?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count ++, organization.getName());
                psst.setLong(count ++, organization.getParentId());
                psst.setString(count ++, organization.getParentIds());
                psst.setBoolean(count ++, organization.getAvailable());
                return psst;
            }
        }, keyHolder);
        organization.setId(keyHolder.getKey().longValue());
        return  organization;
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        String sql = "update sys_organization set name = ?, parent_id = ?, parent_ids = ?, available = ? where id = ?";
        jdbcTemplate.update(sql, organization.getName(), organization.getParentId(), organization.getParentIds(), organization.getAvailable(), organization.getId());
        return organization;
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        Organization organization = findOne(organizationId);
        String deleteSelfSql = "delete from sys_organization where id = ?";
        jdbcTemplate.update(deleteSelfSql, organizationId);
        String deleteDescendantsSql = "delete from sys_organization where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, organization.makeSelfAsParentIds() + "%");
    }

    @Override
    public List<Organization> findAll() {
        String sql = "select id, name, parent_id, parent_ids, available from sys_organization";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Organization>(Organization.class));
    }

    @Override
    public List<Organization> findAllWithExclude(Organization organization) {
        String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id != ? and parent_ids not like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), organization.getId(), organization.makeSelfAsParentIds() + "%");
    }

    @Override
    public Organization findOne(Long organizationId) {
        String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id = ?";
        List<Organization> organizationList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Organization>(Organization.class), organizationId);
        if (organizationList.size() == 0) {
            return null;
        }
        return organizationList.get(0);
    }

    @Override
    public void move(Organization source, Organization target) {
        String moveSourceSql = "update sys_organization set parent_id = ?, parent_ids = ? where id = ?";
        jdbcTemplate.update(moveSourceSql, target.getId(), target.getParentIds(), source.getId());
        String moveSourceDescendantsSql = "update sys_organization set parent_ids=concat(?, substring(parent_ids, length(?))) where parent_ids like ?";
        jdbcTemplate.update(moveSourceDescendantsSql, target.makeSelfAsParentIds(), source.makeSelfAsParentIds(), source.makeSelfAsParentIds() + "%");
    }
}
