package cn.eakay.services;

import cn.eakay.models.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by xialei on 16/4/16.
 */
public class UserHystrixCommand extends HystrixCommand<User> {

    public UserHystrixCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("demo"));
    }

    @Override
    public User run() throws Exception {

//        Thread.sleep(2000);
        return new User("demo", "123456");
    }

    @Override
    public User getFallback() {
        return new User("fallback", "123456");
    }
}
