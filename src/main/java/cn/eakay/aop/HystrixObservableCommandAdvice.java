package cn.eakay.aop;

import cn.eakay.aop.annotation.MyHystrixObservableCommand;
import cn.eakay.domain.MethodInvokeData;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Subscriber;

import java.lang.reflect.Method;

/**
 * Created by xialei on 16/4/16.
 */

@Slf4j
@Aspect
@Component
public class HystrixObservableCommandAdvice {

    @Around(value = "execution(* *(..)) && @annotation(hystrixObservableCommand)", argNames = "pjp,hystrixObservableCommand")
    public Object excuteCommand(final ProceedingJoinPoint pjp, MyHystrixObservableCommand hystrixObservableCommand) throws Exception{
        MethodInvokeData fallbackMethod = null;
        if(!hystrixObservableCommand.resumeWithFallbackMethod().equals("")){
            fallbackMethod = generateresumeWithFallbackMethod(pjp, hystrixObservableCommand.resumeWithFallbackMethod());
        }
        return generateHystrixObservableCommand(pjp, fallbackMethod).observe().toBlocking().toFuture().get();
    }

    private HystrixObservableCommand<Object> generateHystrixObservableCommand(final ProceedingJoinPoint pjp, MethodInvokeData fallbackMethod) {
        return new HystrixObservableCommand<Object>(setter()) {
            @Override
            protected Observable<Object> construct() {
                return Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            Thread.sleep(2000);
                            subscriber.onNext(pjp.proceed());
                            subscriber.onCompleted();
                        } catch (Throwable ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
            }

            @Override
            protected Observable<Object> resumeWithFallback() {
                if (fallbackMethod == null)
                    return super.resumeWithFallback();
                else {
                    return Observable.create(new Observable.OnSubscribe<Object>() {
                        @Override
                        public void call(Subscriber<? super Object> subscriber) {
                            try {
                                subscriber.onNext(fallbackMethod.getMethod().invoke(fallbackMethod.getObj(), fallbackMethod.getParams()));
                                subscriber.onCompleted();
                            } catch (Exception ex) {
                                subscriber.onError(ex);
                            }
                        }
                    });
                }
            }
        };
    }

    private HystrixObservableCommand.Setter setter() {
        return HystrixObservableCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group-name"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("command-name"));
    }

    private MethodInvokeData generateresumeWithFallbackMethod(final ProceedingJoinPoint pjp, String methodName) throws NoSuchMethodException{
        Class clz = pjp.getTarget().getClass();
        Object[] args = pjp.getArgs();
        Object targetObj = pjp.getTarget();
        Class[] paramClzs = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramClzs[i] = args[i].getClass();
        }
        Method method = clz.getMethod(methodName, paramClzs);
        return new MethodInvokeData(targetObj, method, args);
    }

}
