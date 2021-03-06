package cn.eakay.controllers;

import cn.eakay.services.TestHystrixService;
import cn.eakay.services.UserHystrixCommand;
import cn.eakay.services.UserHystrixObservableCommand;
import cn.eakay.services.UserService;
import cn.eakay.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser() {
        return userService.getUser();
    }

    @RequestMapping(value = "/getUser/{username}/{password}", method = RequestMethod.GET)
    public User getUserByParam(@PathVariable(value = "username") String username, @PathVariable(value = "password")String password) throws Exception{
        return userService.getUserByParam(username, password);
    }

    @RequestMapping(value = "/getText", method = RequestMethod.GET)
    public String getText() {
        return "Hello World";
    }

    @RequestMapping(value = "/getUserHystrix", method = RequestMethod.GET)
    public User getUserHystrix() throws Exception{
        //同步执行
        return new UserHystrixCommand().execute();
    }

    @RequestMapping(value = "/getUserHystrixObservable", method = RequestMethod.GET)
    public User getUserHystrixObservable() throws Exception {
        return new UserHystrixObservableCommand().observe().toBlocking().toFuture().get();
    }

}
