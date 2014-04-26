package com.jd.mvc.core.service.impl;

import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpServletRequest;
import com.jd.mvc.common.RouteInfo;
import com.jd.mvc.controller.annotation.FormParam;
import com.jd.mvc.controller.annotation.MethodType;
import com.jd.mvc.controller.annotation.QueryParam;
import com.jd.mvc.controller.annotation.Route;
import com.jd.mvc.controller.annotation.RouteParam;
import com.jd.mvc.core.interceptot.DefaultInterceptot;
import com.jd.mvc.core.service.IBaseCoreMvcService;
import com.jd.mvc.core.util.MvcPageContextUtil;
import com.jd.mvc.core.util.MvcPageUtil;
import com.jd.mvc.core.util.ScanClassPathUtil;
import com.jd.mvc.core.util.SpringContextUtil;
import com.jd.mvc.core.util.StringUtil;
/**
 * 
 * @author liubing1@jd.com
 *
 */
public  class BaseCoreMvcServiceImpl implements IBaseCoreMvcService{
	

	public ConcurrentLinkedQueue<RouteInfo> getRouteInfos(String packageDir, String filters) {
		// TODO Auto-generated method stub
		//ConcurrentLinkedQueue<E>
		ConcurrentLinkedQueue<RouteInfo> routeInfos = new ConcurrentLinkedQueue<RouteInfo>();
		List<String> classFilters = new ArrayList<String>();
		classFilters.add(filters);
		// 创建一个扫描处理器，排除内部类 扫描符合条件的类
		ScanClassPathUtil handler = new ScanClassPathUtil(true,true,classFilters);
		// 递归扫描包 下符合自定义过滤规则的类
		Set<Class<?>> clazzList = handler.getPkgClassAll(packageDir,true);
		for(Class<?> clazz:clazzList){

			Method[] methods = clazz.getMethods();

			List<Method> methodList = new ArrayList<Method>();
			//循环获取所有的方法 除了 Result_JsonTxt或者Result_JsonFile方法
			for(int i = 0;i < methods.length;i ++ ){
				Method m = methods[i];
				if( ! "Result_JsonTxt".equalsIgnoreCase(m.getName()) || ! "Result_JsonFile".equalsIgnoreCase(m.getName()) || ! "ForwardPage".equalsIgnoreCase(m.getName())){
					methodList.add(m);
				}
			}
			for(Method method:methodList){
				if(method.isAnnotationPresent(Route.class)){
					RouteInfo routeInfo = new RouteInfo();
					Route pathMethod = method.getAnnotation(Route.class);
					if(null != pathMethod){
						routeInfo.setClazz(clazz.getName());
						routeInfo.setMethod(method.getName());
						boolean boo = clazz.isAnnotationPresent(Route.class);
						Route pathClazz = null;
						if(boo){
							pathClazz = clazz.getAnnotation(Route.class);
						}
						String path = ((null != pathClazz)?MvcPageUtil.deleteRightBar(pathClazz.value()):"") + MvcPageUtil.deleteRightBar(pathMethod.value());
						routeInfo.setRoute(path.trim());
						routeInfo.setRouteLength(path.split("/").length);
					}
					MethodType methodType = method.getAnnotation(MethodType.class);
					if(null != methodType){
						routeInfo.setCallMethod(methodType.type().toString());
					}
					routeInfos.add(routeInfo);
				}
			}
		}
		return routeInfos;
	}

	

	public boolean isImplementsEasyPageListener(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public ConcurrentHashMap<String, Object> isRouteInfos(ConcurrentLinkedQueue<RouteInfo> routeInfos,
			String route, String methodType) throws Exception{
		// TODO Auto-generated method stub
		ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<String,Object>();
		//ConcurrentHashMap<String, Object>
		LinkedList<RouteInfo> ris = new LinkedList<RouteInfo>();
		LinkedList<RouteInfo> ris_2 = new LinkedList<RouteInfo>();
		String inUrl = MvcPageUtil.deleteRightBar(route);
		String[] inUrls = inUrl.split("/");
		int inUrlsLen = inUrls.length;
		for(RouteInfo routeInfo:routeInfos){
			if(!routeInfo.getRoute().contains("{")&&!routeInfo.getRoute().contains("}")){
				if(routeInfo.getRoute().equals(inUrl)&&routeInfo.getCallMethod().equalsIgnoreCase(methodType)){
					ris_2.add(routeInfo);
				}
			}else{
				if(routeInfo.getRouteLength() == inUrlsLen&&routeInfo.getCallMethod().equalsIgnoreCase(methodType)){
					ris_2.add(routeInfo);
				}
			}
			
		}
		if(StringUtil.isNullOrEmpty(ris_2)||ris_2.size()==0){
			throw new Exception("none one exist");
		}else if(ris_2.size()>1){
			throw new Exception("more than one exist");
		}
		for(int i = 0;i < ris_2.size();i ++ ){
			List<Map<String,String>> routes = new ArrayList<Map<String,String>>();
			List<Map<String,String>> params = new ArrayList<Map<String,String>>();
			RouteInfo routeInfo = ris_2.get(i);

			String[] mUrls = MvcPageUtil.deleteRightBar(routeInfo.getRoute()).split("/");

			for(int j = 1;j < mUrls.length;j ++ ){
				if( ! mUrls[j].equalsIgnoreCase(inUrls[j])){
					String sUrlLeft = mUrls[j].substring(0,1);
					String sUrlRight = mUrls[j].substring(mUrls[j].length() - 1,mUrls[j].length());
					if(sUrlLeft.equalsIgnoreCase("{") && sUrlRight.equalsIgnoreCase("}")){//例子:{demo2}/demo2.html
						Map<String,String> paramMap = new HashMap<String,String>();
						paramMap.put(mUrls[j].toString(),MvcPageUtil.deleteBigBrackets(inUrls[j].toString()));
						params.add(paramMap);
					}else if(sUrlLeft.equalsIgnoreCase("{") &&!sUrlRight.equalsIgnoreCase("}")&&mUrls[j].toString().substring(mUrls[j].toString().indexOf("}")+1, mUrls[j].toString().indexOf("}")+2).equals(".")){//例子:/{demo2}.html
						Map<String,String> paramMap = new HashMap<String,String>();
						String value=inUrls[j].toString().substring(0, inUrls[j].toString().indexOf("."));
						String key=mUrls[j].toString().substring(mUrls[j].toString().indexOf("{"), mUrls[j].toString().indexOf("}")+1);
						paramMap.put(key, value);
						params.add(paramMap);
					}
				}else{
					Map<String,String> routeMap = new HashMap<String,String>();
					routeMap.put(inUrls[j],mUrls[j]);
					routes.add(routeMap);
				}
				routeInfo.setParams(params);
			}

			int sunParam = routes.size() + params.size();

			if(inUrlsLen - 1 == sunParam){
				ris.add(routeInfo);
			}
		}
		if(!StringUtil.isNullOrEmpty(ris)&&ris.size() == 1){
			if(ris.get(0).getCallMethod().equalsIgnoreCase(methodType)){
				map.put("isRouteInfos",true);
				map.put("routeInfo",ris.get(0));
			}else{
				map.put("isRouteInfos",false);
				map.put("routeInfo",null);
			}
		}else{
			map.put("isRouteInfos",false);
			map.put("routeInfo",null);
		}

		return map;
	}
	
	@SuppressWarnings("static-access")
	public Object methodInvoke(RouteInfo routeInfo, boolean isSupportSpring)
			throws Exception {
		// TODO Auto-generated method stub
		MvcPageContextUtil easyPageContext = new MvcPageContextUtil();
		//HttpServletResponse response = easyPageContext.getResponse();
		HttpServletRequest request = easyPageContext.getRequest();

		String method = request.getMethod();

		String charset = easyPageContext.getCharset();

		Map<String,String> multipartParams = null;

		Map<String,String> putParams = null;

		Object obj = null;

		try{
			boolean isMultipartRequest = MvcPageUtil.isMultipartRequest(request);

			if(isMultipartRequest){
				multipartParams = MvcPageUtil.getMultipartParams(request,charset);
				//multipartParams.putAll(getPutParams(request,charset));
			}

			if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
				if(isMultipartRequest){
					putParams = multipartParams;
				}else{
					putParams = getPutParams(request,charset);
				}
			}

			Class<?> clazz = MvcPageUtil.getClassByClassLoader(MvcPageUtil.getClassLoader(),routeInfo.getClazz());

			Method[] mds = clazz.getMethods();
			for(int i = 0;i < mds.length;i ++ ){
				Method md = mds[i];
				if(routeInfo.getMethod().equals(md.getName())){
					Type[] mdTypes = md.getGenericParameterTypes();
					Annotation[][] annoParams = md.getParameterAnnotations();
					Object[] objs = new Object[annoParams.length];
					for(int j = 0;j < annoParams.length;j ++ ){
						Annotation annotation = annoParams[j][0];
						if("FormParam".equals(annotation.annotationType().getSimpleName())){
							FormParam param = (FormParam) annotation;
							String pmType = mdTypes[j].toString();
							if(isMultipartRequest){
								objs[j] = MvcPageUtil.createObjectByParamType(pmType,multipartParams.get(param.value()));
							}else{
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,putParams.get(param.value()));
								}else{
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,request.getParameter(param.value()));
								}
							}
						}else if("QueryParam".equals(annotation.annotationType().getSimpleName())){
							QueryParam param = (QueryParam) annotation;
							String pmType = mdTypes[j].toString();
							if(isMultipartRequest){
								objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(multipartParams.get(param.value()),charset));
							}else{
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(putParams.get(param.value()),charset));
								}else{
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(request.getParameter(param.value()),charset));
								}
							}
						}else if("RouteParam".equals(annotation.annotationType().getSimpleName())){
							RouteParam param = (RouteParam) annotation;
							String val = MvcPageUtil.getMapValue(routeInfo.getParams(),MvcPageUtil.addBigBrackets(param.value()));
							String pmType = mdTypes[j].toString();
							objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(val,charset));
						}
					}
					Object o = null;
					if(isSupportSpring){
						try{	
							String clsname=clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1);
							o = SpringContextUtil.getBean(clsname);
						}catch(Exception e){
							throw new Exception("Operate spring bean error.Bean Name:" + clazz.getName(),e);
						}
					}else{
						try{
							o = clazz.newInstance();
						}catch(Exception e){
							throw new Exception("New controll bean error.Bean Name:" + clazz.getName(),e);
						}
					}
					Boolean  flag=true;
					Class<?> interceptorclass=null;
					if(md.isAnnotationPresent(Route.class)){
						Route route=md.getAnnotation(Route.class);
						interceptorclass=route.cls();
					}
					
					if(!StringUtil.isNullOrEmpty(interceptorclass)&&interceptorclass.newInstance() instanceof DefaultInterceptot){
						DefaultInterceptot defaultInterceptot=(DefaultInterceptot) interceptorclass.newInstance();
						if(objs.length>0){
							flag=defaultInterceptot.preInterceptot(clazz,routeInfo.getRoute(), md, objs);
						}else{
							flag=defaultInterceptot.preInterceptot(clazz,routeInfo.getRoute(), md, null);
						}	
					}//开始前拦截
					if(flag){//通过
						try{
							md.setAccessible(true);
							if(objs.length>0){						
								obj=md.invoke(o, objs);//执行
							}else{
								obj=md.invoke(o, null);
							}
						}catch(InvocationTargetException e){
							
							throw new Exception(e.getTargetException().getMessage());
						} catch (Throwable e) {
							throw new Exception(e.getMessage());
						}
						
					}
					if(!StringUtil.isNullOrEmpty(interceptorclass)&&interceptorclass.newInstance() instanceof DefaultInterceptot){
						DefaultInterceptot defaultInterceptot=(DefaultInterceptot) interceptorclass.newInstance();
						if(objs.length>0){
							defaultInterceptot.afterInterceptot(clazz,routeInfo.getRoute(), md, objs);
						}else{
							defaultInterceptot.afterInterceptot(clazz,routeInfo.getRoute(), md, null);
						}	
					}//方法后拦截
					break;
				}
			}
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return obj;
	}
	public Map<String,String> getPutParams(HttpServletRequest request,String charset){
		InputStreamReader in = null;
		try{
			//request.getParameterMap()
			in = new InputStreamReader(request.getInputStream(),charset);
		}catch(Exception e){
			// e.printStackTrace();
		}

		String urlParam = MvcPageUtil.getServletInputStream(in);
		return MvcPageUtil.getUrlParamByKey(urlParam);
	}

}
