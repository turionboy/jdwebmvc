package com.jd.mvc.core.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jd.mvc.cache.face.ICache;
import com.jd.mvc.cache.face.impl.CacheImpl;
import com.jd.mvc.common.RouteInfo;
import com.jd.mvc.core.service.IBaseCoreMvcService;
import com.jd.mvc.core.service.impl.BaseCoreMvcServiceImpl;
import com.jd.mvc.core.util.MvcPageContextUtil;
import com.jd.mvc.core.util.MvcPageUtil;
import com.jd.mvc.core.util.StringUtil;
import com.jd.mvc.core.util.WebUtil;
/**
 * 框架核心控制层
 * Servlet implementation class CoreServlert
 */
public   class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Log logger=LogFactory.getLog(getClass());
     
    private static ICache<String,ConcurrentLinkedQueue<RouteInfo> > cache;
     
     /**
 	 * 项目包的根路径
 	 */
 	@SuppressWarnings("unused")
	private String rootPkg = "";

 	/**
 	 * Controller 过滤
 	 */
 	@SuppressWarnings("unused")
	private String controllerFilters = "";
 	
 	private static Boolean isSupportSpring;
 	/**
 	 * 路由集合
 	 */
 	private  ConcurrentLinkedQueue<RouteInfo> routeInfos;
   
    /**
     * MVC 核心核心控制层，
     * 判断http是ajax请求，那么直接print参数
     * 如果不是直接跳转
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		// 缓存处理
		res.setHeader("Pragma","No-cache");
		res.setHeader("Cache-Control","no-cache");
		res.setDateHeader("Expires", - 1);
		MvcPageContextUtil.threadLocalRequest.set(req);
		MvcPageContextUtil.threadLocalResponse.set(res);
		MvcPageContextUtil.threadLocalCharset.set("UTF-8");
    	String method = req.getMethod().toLowerCase();
		String pathInfo = req.getServletPath();
		IBaseCoreMvcService coreMvcService=new BaseCoreMvcServiceImpl();
		routeInfos=cache.get("mvc_roteinfo");
		String reult="";
		try {
		ConcurrentHashMap<String,Object> map = coreMvcService.isRouteInfos(routeInfos,pathInfo,method);
		
		if(Boolean.parseBoolean(map.get("isRouteInfos").toString())){
			RouteInfo routeInfo = (RouteInfo) map.get("routeInfo");
			reult=(String) coreMvcService.methodInvoke(routeInfo,isSupportSpring);	
		  }
		} catch (Exception e) {
			 handleActionMethodException(req, res, e);
		}
		if(MvcPageUtil.isAjaxRequest(MvcPageContextUtil.getRequest())&&!StringUtil.isNullOrEmpty(reult)){//ajax请求
			
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/plain");
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out =res.getWriter();
			out.print(reult);
		}else if(!StringUtil.isNullOrEmpty(reult)){//页面跳转
			res.sendRedirect(reult);
		}
    }
	
	/**
	 * 初始化加载
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		logger.info("jdmvc服务开始启动-----------------");
		String  rootPkg=getInitParameter("rootPkg");
		String  controllerFilters=getInitParameter("controllerFilters");
		isSupportSpring=Boolean.parseBoolean(getInitParameter("isSupportSpring"));
		IBaseCoreMvcService coreMvcService=new BaseCoreMvcServiceImpl();
		routeInfos=coreMvcService.getRouteInfos(rootPkg, controllerFilters);
		cache=new CacheImpl<String, ConcurrentLinkedQueue<RouteInfo>>(routeInfos.size());
		cache.put("mvc_roteinfo", routeInfos);
		logger.info("---------------jdmvc结束----------------------------");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	private void handleActionMethodException(HttpServletRequest request, HttpServletResponse response, Exception e) {
            // 若为认证异常，则分两种情况进行处理
            if (WebUtil.isAJAX(request)) {
                // 若为 AJAX 请求，则发送 403 错误
                WebUtil.sendError(403, response,e.getMessage());
                request.setAttribute("javax.servlet.error.exception", e);
            } else {
                // 否则重定向到首页
                WebUtil.redirectRequest(request.getContextPath() + "/", response);
            }
        
    }
}
