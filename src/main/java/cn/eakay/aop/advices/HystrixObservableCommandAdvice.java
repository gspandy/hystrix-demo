package cn.eakay.aop.advices;

import cn.eakay.aop.annotations.MyHystrixObservableCommand;
import cn.eakay.domains.MethodInvokeData;
import cn.eakay.utils.AopUtils;
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
    public Object excuteCommand(final ProceedingJoinPoint pjp, MyHystrixObservableCommand hystrixObservableCommand) throws Exception {
        MethodInvokeData fallbackMethod = hystrixObservableCommand.resumeWithFallbackMethod().equals("") ? null :
                AopUtils.generateMethodInvokeData(pjp, hystrixObservableCommand.resumeWithFallbackMethod());
        return generateHystrixObservableCommand(pjp, fallbackMethod).observe().toBlocking().toFuture().get();
    }

    private HystrixObservableCommand<Object> generateHystrixObservableCommand(final ProceedingJoinPoint pjp, MethodInvokeData fallbackMethod) {
        return new HystrixObservableCommand<Object>(setter()) {
            @Override
            protected Observable<Object> construct() {
                //lambdas 表达式
                return Observable.create(subscriber -> {
                        try {
                            subscriber.onNext(pjp.proceed());
                            subscriber.onCompleted();
                        } catch (Throwable ex) {
                            subscriber.onError(ex);
                        }
                    }
                );
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

    //TODO 动态注册group-name和command-name
    private HystrixObservableCommand.Setter setter() {
        return HystrixObservableCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group-name"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("command-name"));
    }

}
