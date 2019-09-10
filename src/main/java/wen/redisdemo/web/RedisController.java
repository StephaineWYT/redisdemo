package wen.redisdemo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wen.redisdemo.entity.User;
import wen.redisdemo.mapper.UserMapper;

@RestController
public class RedisController {

    protected static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/setUser")
    public void setUser(Long id, String username, String password) {
        logger.debug("setUser:id={}, username={}, password={}", id, username, password);
        User user = new User(id, username, password);
        userMapper.saveUser(user);
    }

    @RequestMapping("/getUser")
    public String getUser(Long id) {
        return "username:" + userMapper.getUser(id).getUsername() + "\n" +
                "password: " + userMapper.getUser(id).getPassword();
    }

}
