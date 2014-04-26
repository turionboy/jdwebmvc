
package com.jd.mvc.javassist.proxy.support;

import java.lang.reflect.Method;

import com.jd.mvc.core.proxy.ProxyHandler;

public class HandlerCallingTarget<T> extends ProxyHandler<T>{

    public Method m;
    
    public Object[] replacementArgs;
    
    public HandlerCallingTarget(T instance, Object[] replacementArgs) {
        super(instance);
        this.replacementArgs = replacementArgs;
    }
    
    @Override
    protected boolean finalCallInHandler(Method m) {
        return true;
    }

    @Override
    protected Object invokeMethod(T instance, Method m, Object[] args) {
        this.m = m;
        
        try {
            return m.invoke(instance, replacementArgs);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
