package wen.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(final String key, Object value) {
        try {
            ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
        } catch (Exception e) {
            System.out.println("set error: key {" + key + "}, value {\" + value + \"}");
        }
    }

    public Object get(final String key) {
        ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

}
