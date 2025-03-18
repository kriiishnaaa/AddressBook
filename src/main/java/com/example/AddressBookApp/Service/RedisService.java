package com.example.AddressBookApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    StringRedisTemplate redisTemplate;

    // Store token in Redis (Login)
    public void saveToken(String userId, String token) {
        redisTemplate.opsForValue().set("JWT:" + userId, token, 1, TimeUnit.MINUTES);
    }

    // Retrieve token from Redis
    public String getToken(String userId) {
        return redisTemplate.opsForValue().get("JWT:" + userId);
    }

    // Delete token from Redis (Logout)
    public void deleteToken(String userId) {
        redisTemplate.delete("JWT:" + userId);
    }


}
