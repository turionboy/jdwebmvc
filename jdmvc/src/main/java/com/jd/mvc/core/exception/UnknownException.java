package com.jd.mvc.core.exception;

/**
 * 未知异常
 * @author liubing
 *
 */
public class UnknownException extends Exception{

	private static final long serialVersionUID = 6312480365496692204L;

	public UnknownException(){

	}

	public UnknownException(String message,Throwable e){
		super(message,e);
	}

	public UnknownException(String message){
		super(message);
	}
}
