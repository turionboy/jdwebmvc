package com.demo.interceptot;

import java.lang.reflect.Method;

import com.jd.mvc.core.interceptot.DefaultInterceptot;

public class demoInterceptot implements DefaultInterceptot{

	@Override
	public Boolean preInterceptot(Class<?> cls,String routepath, Method method, Object... params) {
		// TODO Auto-generated method stub
		//System.out.println(cls+"------"+method+"--------"+routepath+"------"+params);
		return true;
	}

	@Override
	public void afterInterceptot(Class<?> cls,String routepath, Method method, Object... params) {
		// TODO Auto-generated method stub
		
	}

}
