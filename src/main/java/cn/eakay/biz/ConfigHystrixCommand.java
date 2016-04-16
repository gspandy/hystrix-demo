package cn.eakay.biz;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Created by xialei on 16/4/16.
 */
public class ConfigHystrixCommand extends HystrixCommand {


    public static Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group-key"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("command-key"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("threadpool-key"));

    public ConfigHystrixCommand() {
        super(setter);
    }

    @Override
    protected Object run() throws Exception {
        return null;
    }
}



