package cn.eakay.utils;

import cn.eakay.domains.MethodInvokeData;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by adrian on 2016/4/17.
 */
public class AopUtils {

    public static MethodInvokeData generateMethodInvokeData(final ProceedingJoinPoint pjp, String methodName) throws NoSuchMethodException{
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
