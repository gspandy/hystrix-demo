package cn.eakay.domains;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Created by xialei on 16/4/16.
 */
@Data
public class MethodInvokeData {

    private Object obj;
    private Method method;
    private Object[] params;

    public MethodInvokeData(Object obj, Method method, Object[] params){
        this.obj = obj;
        this.method = method;
        this.params = params;
    }
}
