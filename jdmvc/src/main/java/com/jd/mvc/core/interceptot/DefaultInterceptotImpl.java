package com.jd.mvc.core.interceptot;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class DefaultInterceptotImpl implements DefaultInterceptot {
	
	private Log logger=LogFactory.getLog(getClass());
	@Override
	public Boolean preInterceptot(Class<?> cls, String routepath,
			Method method, Object... params) {
		// TODO Auto-generated method stub
		logger.info("访问路径:"+routepath);
		return true;
	}

	@Override
	public void afterInterceptot(Class<?> cls, String routepath, Method method,
			Object... params) {
		// TODO Auto-generated method stub
	}

}
