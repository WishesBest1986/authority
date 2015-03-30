package com.neusoft.authority.dao;

import com.neusoft.authority.entity.User;

import java.util.List;

/**
 * Created by admin on 2015/3/25.
 */
public interface UserDao {
    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long userId);
    public List<User> findAll();
    public User findOne(Long userId);
    public User findByUsername(String username);
}
