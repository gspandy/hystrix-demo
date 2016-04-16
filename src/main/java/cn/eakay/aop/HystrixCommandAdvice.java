package cn.eakay.aop;

import cn.eakay.aop.annotation.MyHystrixCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class HystrixCommandAdvice {
    private String groupName;
    private String commandName;

    @Around("anyMethod() && @annotation(hystrixCommand)")
    public Object runCommand(final ProceedingJoinPoint pjp, MyHystrixCommand hystrixCommand) {
        String fallbackMethod = hystrixCommand.fallbackMethod().equals("") ? null : hystrixCommand.fallbackMethod();
        
        return wrapWithHystrixCommnad(pjp).execute();
    }

    private HystrixCommand<Object> wrapWithHystrixCommnad(final ProceedingJoinPoint pjp) {
        return new HystrixCommand<Object>(setter()) {
            @Override
            protected Object run() throws Exception {
                try {
                    return pjp.proceed();
                } catch (Throwable throwable) {
                    throw (Exception) throwable;
                }
            }

            @Override
            protected Object getFallback() {

                return null;
            }
        };
    }



    private HystrixCommand.Setter setter() {
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandName));
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
