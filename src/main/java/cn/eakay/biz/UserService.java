package cn.eakay.biz;

import cn.eakay.aop.annotation.MyHystrixObservableCommand;
import cn.eakay.domain.User;
import org.springframework.stereotype.Service;

/**
 * Created by xialei on 16/4/16.
 */

@Service
public class UserService {


    @MyHystrixObservableCommand()
    public User getUser() {
        System.out.println(22222222);
        return new User("demo", "123456");
    }

    public User getUserDefault() {
        return new User("fallback", "123456");
    }

}
