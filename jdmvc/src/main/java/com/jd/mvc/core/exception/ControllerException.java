package com.jd.mvc.core.exception;



import javax.servlet.ServletException;

import com.jd.mvc.core.util.MvcPageContextUtil;
/**
 * 控制层异常
 * @author liubing
 *
 */
public class ControllerException extends ServletException{

	private static final long serialVersionUID = - 1690248662374373542L;

	public ControllerException(){

	}

	public ControllerException(String message,Throwable e){
		super(message,e);
	}

	public ControllerException(String message){
		/*IEhCacheService ehCacheService =(IEhCacheService) SpringContextUtil.getBean("cacheService");
		ehCacheService.put("errorMessage", message);*/
		super(message);
		try {
			//MvcPageContextUtil.getResponse().setContentType("text/javascript;charset=UTF-8");
			MvcPageContextUtil.getResponse().sendError(500, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}
}
