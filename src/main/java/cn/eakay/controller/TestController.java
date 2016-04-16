package cn.eakay.controller;

import cn.eakay.biz.TestHystrixService;
import cn.eakay.biz.UserHystrixCommand;
import cn.eakay.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xialei on 16/4/15.
 */

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestHystrixService hystrixService;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser() {
        return new User("demo", "123456");
    }

    @RequestMapping(value = "/getText", method = RequestMethod.GET)
    public String getText() {
        return "Hello World";
    }

    @RequestMapping(value = "/getUserHystrix", method = RequestMethod.GET)
    public User getUserHystrix() throws Exception{
        return new UserHystrixCommand().execute();
    }

}
