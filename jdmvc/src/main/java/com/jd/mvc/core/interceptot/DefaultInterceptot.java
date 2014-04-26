package com.jd.mvc.core.interceptot;

import java.lang.reflect.Method;

/**
 * 拦截器
 * @author liubing1@jd.com
 *
 */
public interface DefaultInterceptot {
	/**
	 * 方法开始前拦截
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param cls
	* @param routepath
	* @param method
	* @param params
	* @return
	 */
	public Boolean preInterceptot(Class<?> cls,String routepath,Method method,Object...params);
	/**
	 * 方法后开始拦截
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param cls
	* @param method
	* @param params
	 */
	public void afterInterceptot(Class<?> cls,String routepath,Method method,Object...params);
}
