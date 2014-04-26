package com.jd.mvc.core.exception;

/**
 * 操作springbeans 异常
 * @author liubing
 *
 */
public class OperateSpringBeanException extends Exception{
	private static final long serialVersionUID = - 2714173997438832084L;

	public OperateSpringBeanException(){

	}

	public OperateSpringBeanException(String message,Throwable e){
		super(message,e);
	}

	public OperateSpringBeanException(String message){
		super(message);
	}
}
