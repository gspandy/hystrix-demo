package cn.eakay.biz;

import cn.eakay.domain.User;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * Created by xialei on 16/4/15.
 */

@Service
public class TestHystrixService {

//    @HystrixCommand(fallbackMethod = "getUserFallback")
    public User getUser() throws Exception{
        Thread.sleep(1000);
        return new User("demo", "123456");
    }

    public User getUserFallback() {
        return new User("fallback", "123456");
    }


}
