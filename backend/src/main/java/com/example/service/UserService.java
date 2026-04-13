package com.example.service;

import com.example.cache.LocalCache;
import com.example.config.JwtConfig;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LocalCache localCache;

    @Autowired
    private JwtConfig jwtConfig;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        if (user.getStatus() != 1) {
            return null;
        }
        String token = generateToken(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    private String generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        try {
            String subject = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return Long.parseLong(subject);
        } catch (Exception e) {
            return null;
        }
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public List<User> list() {
        return userMapper.selectAll();
    }

    public void add(User user) {
        user.setId(localCache.generateUserId());
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
