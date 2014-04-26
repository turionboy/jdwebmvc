package com.jd.mvc.core.exception;

/**
 * 初始化异常
 * @author liubing
 *
 */
public class InitMVCPageException extends Exception{
	private static final long serialVersionUID = - 7383905078921716160L;

	public InitMVCPageException(){

	}

	public InitMVCPageException(String message,Throwable e){
		super(message,e);
	}

	public InitMVCPageException(String message){
		super(message);
	}

}
