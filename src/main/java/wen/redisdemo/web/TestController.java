package wen.redisdemo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    protected static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/")
    public String test() {
        logger.debug("测试连接");
        return "连接成功！";
    }

    @RequestMapping("/test/{var}")
    public String testVar(@PathVariable String var) {
        logger.debug("var={}", var);
        return "test " + var;
    }

}
