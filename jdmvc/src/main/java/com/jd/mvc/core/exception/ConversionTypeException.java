package com.jd.mvc.core.exception;

/**
 * 类型转化异常
 * @author liubing
 *
 */
public class ConversionTypeException extends Exception{
	private static final long serialVersionUID = - 6949210025082062507L;

	public ConversionTypeException(){

	}

	public ConversionTypeException(String message,Throwable e){
		super(message,e);
	}

	public ConversionTypeException(String message){
		super(message);
	}
}
