package com.diary.calendar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Value("${app.jwt.expiration}")
    private Long expiration;

    @Value("${app.redis.key-prefix}")
    private String redisKeyPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 生成Token并存储到Redis
     */
    public String generateAndStoreToken(Integer userId) {
        // 生成Token (这里使用UUID，生产环境建议用JWT)
        String token = UUID.randomUUID().toString();

        // 存储到Redis
        String key = redisKeyPrefix + token;
        redisTemplate.opsForValue().set(key, userId, expiration, TimeUnit.SECONDS);

        return token;
    }

    /**
     * 验证Token有效性
     */
    public boolean validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        String key = redisKeyPrefix + token;
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据Token获取用户信息
     */
    public Integer getUserIdByToken(String token) {
        if (!validateToken(token)) {
            return null;
        }
        String key = redisKeyPrefix + token;
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除Token（退出登录）
     */
    public void deleteToken(String token) {
        if (token != null && !token.trim().isEmpty()) {
            String key = redisKeyPrefix + token;
            redisTemplate.delete(key);
        }
    }

    /**
     * 刷新Token过期时间
     */
    public void refreshToken(String token) {
        if (validateToken(token)) {
            String key = redisKeyPrefix + token;
            Integer userId = getUserIdByToken(token);
            if (userId != null) {
                redisTemplate.opsForValue().set(key, userId, expiration, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 获取Token剩余过期时间
     */
    public Long getTokenExpire(String token) {
        String key = redisKeyPrefix + token;
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}