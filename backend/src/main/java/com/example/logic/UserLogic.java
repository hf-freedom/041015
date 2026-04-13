package com.example.logic;

import com.example.cache.LocalCache;
import com.example.config.JwtConfig;
import com.example.entity.User;
import com.example.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserLogic {

    private static final Logger logger = LoggerFactory.getLogger(UserLogic.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LocalCache localCache;

    @Autowired
    private JwtConfig jwtConfig;

    public Map<String, Object> login(String username, String password) {
        logger.info("User login attempt: {}", username);
        User user = userService.getByUsername(username);
        if (user == null) {
            logger.warn("Login failed: user not found - {}", username);
            return null;
        }
        if (!user.getPassword().equals(password)) {
            logger.warn("Login failed: password incorrect - {}", username);
            return null;
        }
        if (user.getStatus() != 1) {
            logger.warn("Login failed: user disabled - {}", username);
            return null;
        }
        String token = generateToken(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        logger.info("User login success: {} (ID: {})", username, user.getId());
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

    public List<User> listUsers() {
        logger.debug("Fetching all users");
        return userService.list();
    }

    public User getUserById(Long id) {
        logger.debug("Fetching user by id: {}", id);
        return userService.getById(id);
    }

    public void addUser(User user) {
        logger.info("Adding new user: {}", user.getUsername());
        user.setId(localCache.generateUserId());
        userService.add(user);
        logger.info("User added successfully: {} (ID: {})", user.getUsername(), user.getId());
    }

    public void updateUser(User user) {
        logger.info("Updating user: {}", user.getId());
        userService.update(user);
        logger.info("User updated successfully: {}", user.getId());
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user: {}", id);
        userService.delete(id);
        logger.info("User deleted successfully: {}", id);
    }

    public void updateProfile(User user) {
        logger.info("Updating profile for user: {}", user.getId());
        userService.updateProfile(user);
        logger.info("Profile updated successfully for user: {}", user.getId());
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        logger.info("Updating password for user: {}", userId);
        userService.updatePassword(userId, oldPassword, newPassword);
        logger.info("Password updated successfully for user: {}", userId);
    }
}
