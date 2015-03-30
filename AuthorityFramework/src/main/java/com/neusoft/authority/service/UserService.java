package com.neusoft.authority.service;

import com.neusoft.authority.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2015/3/26.
 */
public interface UserService {
    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long userId);

    public void changePassword(Long userId, String newPassword);

    public List<User> findAll();
    public User findOne(Long userId);
    public User findByUsername(String username);
    public Set<String> findRoles(String username);
    public Set<String> findPermissions(String username);
}
