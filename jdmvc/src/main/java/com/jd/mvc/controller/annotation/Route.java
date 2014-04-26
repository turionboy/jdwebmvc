package com.jd.mvc.controller.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jd.mvc.core.interceptot.DefaultInterceptotImpl;
/**
 * 
 * <p>Copyright: All Rights Reserved</p>  
 * <p>Description: 地址路由类 注解类 </p> 
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Route{
	public String value() default "";
	
	public Class<?> cls() default DefaultInterceptotImpl.class;//拦截类
	
	
}
