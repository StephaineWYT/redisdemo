package wen.redisdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    protected static Logger logger = LoggerFactory.getLogger(RedisService.class);

    public void set(final String key, Object value) {
        try {
            ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
        } catch (Exception e) {
            logger.error("set error: key {}, value {}", key, value);
        }
    }

    public Object get(final String key) {
        ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

}
