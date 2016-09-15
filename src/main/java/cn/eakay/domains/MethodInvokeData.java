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

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
    
    
}
