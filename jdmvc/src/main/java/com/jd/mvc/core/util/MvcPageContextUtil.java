package com.jd.mvc.core.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * MVC 框架核心 上下文
 * @author liubing
 *
 */
public class MvcPageContextUtil{

	public static ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();
	public static ThreadLocal<HttpServletResponse> threadLocalResponse = new ThreadLocal<HttpServletResponse>();
	public static ThreadLocal<String> threadLocalPageSuffix = new ThreadLocal<String>();
	public static ThreadLocal<String> threadLocalCharset = new ThreadLocal<String>();
	public static ThreadLocal<ServletContext> threadLocalServletContext = new ThreadLocal<ServletContext>();
	private Logger log =LoggerFactory.getLogger(MvcPageContextUtil.class);
	/**
	 * 
	 * <br/>Description:获取返回页面的后缀
	 * 
	 * 
	 * @return
	 */
	public String getPageSuffix(){
		return threadLocalPageSuffix.get();
	}

	/**
	 * 
	 * <br/>Description:获取 request
	 * 
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return threadLocalRequest.get();
	}

	/**
	 * 
	 * <br/>Description:获取 response
	 * 
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return threadLocalResponse.get();
	}

	/**
	 * 
	 * <br/>Description:获取 session
	 * 
	 * 
	 * @return
	 */
	public HttpSession getSession(){
		return threadLocalRequest.get().getSession();
	}
	
	
	/**
	 * 
	 * <br/>Description:获取 ServletContext
	 * 
	 * 
	 * @return
	 */
	public static ServletContext getServletContext(){
		return threadLocalServletContext.get();
	}
	
	public String getCharset(){
		return threadLocalCharset.get();
	}
	
	/**
	 * 
	 * <br/>Description:跳转到一个页面
	 * 
	 * 
	 * @param path
	 */
	public void doDispatcherForward(String path){
		try{
			threadLocalRequest.get().getRequestDispatcher(path).forward(threadLocalRequest.get(),threadLocalResponse.get());

		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
}
