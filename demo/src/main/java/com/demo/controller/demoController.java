package com.demo.controller;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import com.demo.interceptot.demoInterceptot;
import com.jd.mvc.controller.action.BaseController;
import com.jd.mvc.controller.annotation.MethodType;
import com.jd.mvc.controller.annotation.MethodType.mType;
import com.jd.mvc.controller.annotation.FormParam;
import com.jd.mvc.controller.annotation.QueryParam;
import com.jd.mvc.controller.annotation.Route;
import com.jd.mvc.controller.annotation.RouteParam;
import com.jd.mvc.core.util.MvcPageContextUtil;
import com.jd.mvc.core.util.MvcPageUtil;
/**
 * 控制层
 * @author liubingsmile@gmail.com
 *
 */
@Route(value="/demo")
@Controller(value="demoController")
public class demoController extends BaseController{
	/**
	 * demo 不带参数
	* <p>Title: </p> 
	* <p>URL:http://127.0.0.1:8080/demo/demo/demo.html </p> 
	* @throws Exception
	 */
	@Route(value="/demo.html")
	@MethodType(type=mType.get)
	public void demo() throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, "1111");
	}
	/**
	 * demo2 第二种方式
	* <p>Title: </p> 
	* <p>URL:http://127.0.0.1:8080/demo/demo/demo3/demo2.html </p> 
	* @param demo2
	* @throws Exception
	 */
	@Route(value="/{demo2}/demo2.html")
	@MethodType(type=mType.get)
	public void demo2(@RouteParam("demo2") Map<String, Object> demo2) throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, demo2.get("demo2")+"");
	}
	/**
	 * 参数第种方式
	* <p>Title: </p> 
	* <p>URL:http://127.0.0.1:8080/demo/demo/demo1.html?demo=3 </p> 
	* @param demo
	* @throws Exception
	 */
	@Route(value="/demo1.html")
	@MethodType(type=mType.get)
	public void demo1(@QueryParam("demo") String demo) throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, demo);
	}
	/**
	 * 第三种方式
	* <p>Title: </p> 
	* <p>URL:http://127.0.0.1:8080/demo/demo/demo3.html?demo=3 </p> 
	* @param demo
	* @throws Exception
	 */
	@Route(value="/demo3.html")
	@MethodType(type=mType.get)
	public void demo3(@FormParam("demo") String demo) throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, demo+"_第三种方式");
	}
	/**
	 * 
	* <p>Title: </p> 
	* <p>URL: http://127.0.0.1:8080/demo/demo/demo4/sa/demo6.html</p> 
	* @param demo4
	* @throws Exception
	 */
	@Route(value="/demo4/{sa}/{demo4}.html",cls=demoInterceptot.class)
	@MethodType(type=mType.get)
	public void demo4(@RouteParam("demo4") Map<String, Object> demo4) throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, demo4.get("demo4")+"");
	}
	
	/**
	 * 参数第种方式
	* <p>Title: </p> 
	* <p>URL:http://127.0.0.1:8080/demo/demo/demo7.html?demo=3&demo1=4 </p> 
	* @param demo
	* @throws Exception
	 */
	@Route(value="/demo7.html")
	@MethodType(type=mType.get)
	public void demo7(@QueryParam("demo") Map<String,Object[]> demo) throws Exception{
		HttpServletResponse response=MvcPageContextUtil.getResponse();
		MvcPageUtil.resultJsonToString(response, demo.get("demo")[0]+"");
		//trans
	}
	/**
	 * 第7种方式
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param demo
	* @return
	* @throws Exception
	 */
	@Route(value="/demo10.html")
	@MethodType(type=mType.get)
	public String demo10(@QueryParam("demo") String demo) throws Exception{
		//HttpServletResponse response=MvcPageContextUtil.getResponse();
		return MvcPageContextUtil.getRequest().getContextPath()+"/index.jsp";
	}
}
