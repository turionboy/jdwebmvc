package com.jd.mvc.core.proxy;

import java.lang.reflect.Method;

public  class DefaultHandlerTarget<T> extends ProxyHandler<T>{

    public Method m;
    public Object[] args;
    public T instance;
    
	public  DefaultHandlerTarget(T instance) {
        super(instance);
    }

    @Override
    protected Object invokeMethod(T instance, Method m, Object[] args) {
        this.m = m;
        this.args = args;
        this.instance = instance;
        return null;
    }
}