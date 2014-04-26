package com.jd.mvc.core.exception;

/**
 * 文件上传异常
 * @author liubing
 *
 */
public class UploadFileException extends Exception{

	private static final long serialVersionUID = 1455948644658383391L;

	public UploadFileException(){

	}

	public UploadFileException(String message,Throwable e){
		super(message,e);
	}

	public UploadFileException(String message){
		super(message);
	}

}
