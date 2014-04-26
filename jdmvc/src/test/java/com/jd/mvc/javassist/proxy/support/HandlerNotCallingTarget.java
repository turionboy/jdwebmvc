
package com.jd.mvc.javassist.proxy.support;

import java.lang.reflect.Method;

import com.jd.mvc.core.proxy.ProxyHandler;


public class HandlerNotCallingTarget<T> extends ProxyHandler<T>{

    public Method m;
    public Object[] args;
    public T instance;
    

    
    public HandlerNotCallingTarget(T instance) {
        super(instance);
    }

    @Override
    protected Object invokeMethod(T instance, Method m, Object[] args) {
        this.m = m;
        this.args = args;
        this.instance = instance;
        System.out.println("OK");
        return null;
    }
    
}
