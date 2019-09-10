package wen.redisdemo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import wen.redisdemo.entity.User;

@Repository
public class UserMapper {

    @Autowired
    RedisTemplate redisTemplate;

    public void saveUser(User user) {
        ValueOperations<Long, User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(user.getId(), user);
        System.out.println(valueOperations.get("1"));
    }

    public User getUser(Long id) {
        ValueOperations<Long, User> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(id);
    }
}
