package cn.eakay.services;

import cn.eakay.models.User;
import org.springframework.stereotype.Service;

/**
 * Created by xialei on 16/4/15.
 */

@Service
public class TestHystrixService {

    public User getUser() throws Exception{
        Thread.sleep(1000);
        return new User("demo", "123456");
    }

    public User getUserFallback() {
        return new User("fallback", "123456");
    }


}
