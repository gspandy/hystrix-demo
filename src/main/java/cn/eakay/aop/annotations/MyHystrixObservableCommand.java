package cn.eakay.aop.annotations;

import java.lang.annotation.*;

/**
 * Created by xialei on 16/4/16.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyHystrixObservableCommand {

    String resumeWithFallbackMethod() default "";

}
