package wen.redisdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import wen.redisdemo.entity.User;
import wen.redisdemo.service.RedisService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisService redisService;

    @Test
    public void contextLoads() {
    }

    /**
     * 取出缓存中的对象
     */
    @Test
    public void testObj() {
        User user = new User(1L, "I", "smile");
        ValueOperations<Long, User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(1L, user);
        User u = valueOperations.get(1L);
        System.out.println(u.toString());
    }

    /**
     * 超时失效测试
     */
    @Test
    public void testExpire() throws InterruptedException {
        User user = new User(2L, "I", "smile");
        ValueOperations<Long, User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(2L, user, 100, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        boolean exist_flag = redisTemplate.hasKey(2L);
        if (exist_flag) {
            System.out.println("EXIST");
        } else {
            System.out.println("NOT EXIST");
        }
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete() {
        ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
        User user = new User(3L, "she", "smiles");
        redisTemplate.opsForValue().set(3L, user);
        redisTemplate.delete(3L);
        boolean exist_flag = redisTemplate.hasKey(3L);
        if (exist_flag) {
            System.out.println("EXIST");
        } else {
            System.out.println("NOT EXIST");
        }
    }

    /**
     * 哈希表测试
     */
    @Test
    public void testHash() {
        HashOperations<Long, User, String> hash = redisTemplate.opsForHash();
        User user = new User(4L, "he", "haha");
        hash.put(4L, user, "lala");
        String value = (String) hash.get(4L, user);
        System.out.println(" 4L " + user.toString() + " : " + value);
    }

    /**
     * 队列
     */
    @Test
    public void testList() {
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.leftPush("listKey", "first to get in");
        list.leftPush("listKey", "second to get in");
        list.leftPush("listKey", "third to get in");
        String value = (String) list.leftPop("listKey");
        System.out.println("list value : " + value.toString());

        List<String> values = list.range("listKey", 0, 2);
        for (String v : values) {
            System.out.println("list range : " + v);
        }
    }

    /**
     * set
     */
    @Test
    public void testSet() {
        String key = "set";
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key, "Hi");
        set.add(key, "My");
        set.add(key, "Stephanie");
        set.add(key, "Stephanie");
        Set<String> values = set.members(key);
        for (String v : values) {
            System.out.println("set value : " + v);
        }
    }

    /**
     * 测试difference
     */
    @Test
    public void TestSetDifference() {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        String key1 = "setOne";
        String key2 = "setTwo";
        set.add(key1, "a");
        set.add(key1, "b");
        set.add(key1, "c");
        set.add(key1, "c");
        set.add(key2, "a");
        set.add(key2, "d");
        Set<String> differ = set.difference(key1, key2);
        for (String v : differ) {
            System.out.println("difference from key1 to key2 : " + v);
        }
    }

    /**
     * 测试 unions
     */
    @Test
    public void TestUnions() {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        String key3 = "setThree";
        String key4 = "setFour";
        set.add(key3, "hi");
        set.add(key3, "my");
        set.add(key3, "love");
        set.add(key4, "Here");
        set.add(key4, "I");
        set.add(key4, "am");
        Set<String> unions = set.union(key3, key4);
        for (String v : unions) {
            System.out.println("unions value : " + v);
        }
    }

    /**
     * 测试 ZSet
     */
    @Test
    public void testZSet() {
        String key = "zSetKey";
        redisTemplate.delete(key);
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.add(key, "hi", 1);
        zSet.add(key, "how", 3);
        zSet.add(key, "are", 2);
        zSet.add(key, "you", 4);

        Set<String> zSetList = zSet.range(key, 0, 3);
        for (String v : zSetList) {
            System.out.println("zSet value : " + v);
        }

        Set<String> zSetTwo = zSet.rangeByScore(key, 0, 3);
        for (String v : zSetTwo) {
            System.out.println("zSetTwo value : " + v);
        }
    }

    @Test
    public void testString() throws Exception {
        redisService.set("Stephanie", "Went");
        Assert.assertEquals("Went", redisService.get("Stephanie"));
    }
}
