package cn.eakay.aop.annotations;

import java.lang.annotation.*;

/**
 * Created by adria on 2016/4/15.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyHystrixCommand {

    String fallbackMethod() default "";

}
