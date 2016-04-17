package cn.eakay.services;

import cn.eakay.aop.annotations.MyHystrixObservableCommand;
import cn.eakay.models.User;
import org.springframework.stereotype.Service;

/**
 * Created by xialei on 16/4/16.
 */

@Service
public class UserService {


    @MyHystrixObservableCommand()
    public User getUser() {
        System.out.println(22222222);
        try {

            Thread.sleep(2000);
        }
        catch (Exception ex) {

        }
        return new User("demo", "123456");
    }

    public User getUserDefault() {
        return new User("fallback", "123456");
    }

    @MyHystrixObservableCommand(resumeWithFallbackMethod = "getUserByParamDefault")
    public User getUserByParam(String username, String password) throws Exception{
        Thread.sleep(2000);
        return new User(username, password);
    }

    public User getUserByParamDefault(String username, String password) {
        return new User("default", "123456");
    }

}
