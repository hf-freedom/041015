package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public List<User> list() {
        return userMapper.selectAll();
    }

    public void add(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    public void update(User user) {
        User existing = userMapper.selectById(user.getId());
        if (existing != null) {
            user.setCreateTime(existing.getCreateTime());
            user.setUpdateTime(LocalDateTime.now());
            userMapper.update(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public void updateProfile(User user) {
        User existing = userMapper.selectById(user.getId());
        if (existing != null) {
            existing.setNickname(user.getNickname());
            existing.setEmail(user.getEmail());
            existing.setPhone(user.getPhone());
            existing.setUpdateTime(LocalDateTime.now());
            userMapper.update(existing);
        }
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            user.setUpdateTime(LocalDateTime.now());
            userMapper.update(user);
        }
    }
}
