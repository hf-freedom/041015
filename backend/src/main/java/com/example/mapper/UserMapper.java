package com.example.mapper;

import com.example.cache.LocalCache;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMapper {

    @Autowired
    private LocalCache localCache;

    public void insert(User user) {
        localCache.saveUser(user);
    }

    public void update(User user) {
        localCache.saveUser(user);
    }

    public void deleteById(Long id) {
        localCache.deleteUser(id);
    }

    public User selectById(Long id) {
        return localCache.getUserById(id);
    }

    public User selectByUsername(String username) {
        return localCache.getUserByUsername(username);
    }

    public List<User> selectAll() {
        return localCache.getAllUsers();
    }
}
