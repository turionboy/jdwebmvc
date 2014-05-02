package com.jd.mvc.core.service.impl;

import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpServletRequest;
import com.jd.mvc.common.RouteInfo;
import com.jd.mvc.controller.annotation.FormParam;
import com.jd.mvc.controller.annotation.MethodType;
import com.jd.mvc.controller.annotation.QueryParam;
import com.jd.mvc.controller.annotation.Route;
import com.jd.mvc.core.interceptot.DefaultInterceptot;
import com.jd.mvc.core.service.IBaseCoreMvcService;
import com.jd.mvc.core.util.MvcPageContextUtil;
import com.jd.mvc.core.util.MvcPageUtil;
import com.jd.mvc.core.util.ScanClassPathUtil;
import com.jd.mvc.core.util.SpringContextUtil;
import com.jd.mvc.core.util.StringUtil;
/**
 * 
 * @author liubingsmile@gmail.com
 *
 */
public  class BaseCoreMvcServiceImpl implements IBaseCoreMvcService{
	//private static final Map<Class<?>, Method[]> CACHE = Collections.synchronizedMap(new WeakHashMap<Class<?>, Method[]>());
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
				if( !Modifier.isStatic(m.getModifiers())&&! "Result_JsonTxt".equalsIgnoreCase(m.getName()) && ! "Result_JsonFile".equalsIgnoreCase(m.getName()) && ! "ForwardPage".equalsIgnoreCase(m.getName())){
					methodList.add(m);
				}
			}
			for(Method method:methodList){
				if(method.isAnnotationPresent(Route.class)){
					RouteInfo routeInfo = new RouteInfo();
					Route pathMethod = method.getAnnotation(Route.class);
					if(null != pathMethod){
						routeInfo.setClazz(clazz.getName());
						routeInfo.setMethods(clazz.getMethods());
						routeInfo.setMethod(method.getName());
						boolean boo = clazz.isAnnotationPresent(Route.class);
						Route pathClazz = null;
						if(boo){
							pathClazz = clazz.getAnnotation(Route.class);
						}
						String path = ((null != pathClazz)?MvcPageUtil.deleteRightBar(pathClazz.value()):"") + MvcPageUtil.deleteRightBar(pathMethod.value());
						routeInfo.setRoute(path.trim());
						routeInfo.setRouteLength(path.split("/").length);
						routeInfo.setInterceptor(pathClazz.cls());
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
		String inUrl = MvcPageUtil.deleteRightBar(route);
		String[] inUrls = inUrl.split("/");
		int inUrlsLen = inUrls.length;
		for(RouteInfo routeInfo:routeInfos){
			if(!routeInfo.getRoute().contains("{")&&!routeInfo.getRoute().contains("}")){
				if(routeInfo.getRoute().equals(inUrl)&&routeInfo.getCallMethod().equalsIgnoreCase(methodType)){//没有URL作为参数匹配
					map.put("isRouteInfos",true);
					map.put("routeInfo",routeInfo);
					return map;
				}
			}else{
				if(routeInfo.getRoute().contains("{")&&routeInfo.getRoute().contains("}")&&routeInfo.getRouteLength() == inUrlsLen&&routeInfo.getCallMethod().equalsIgnoreCase(methodType)){
					String[] mUrls = MvcPageUtil.deleteRightBar(routeInfo.getRoute()).split("/");
					Map<String, Object> params=new HashMap<String, Object>();
					List<String> results=new ArrayList<String>();
					for(int i=0;i<mUrls.length;i++){
						if(!mUrls[i].equals(inUrls[i])){
							if(mUrls[i].contains(".")&&mUrls[i].contains("{")&&mUrls[i].contains("}")){//{demo}.html
								String value=inUrls[i].substring(0, inUrls[i].indexOf("."));
								String key=mUrls[i].substring(mUrls[i].indexOf("{")+1, mUrls[i].indexOf("}"));
								params.put(key, value);
							}else if(!mUrls[i].contains(".")&&mUrls[i].contains("{")&&mUrls[i].contains("}")){
								params.put(mUrls[i].substring(1, mUrls[i].length()-1),MvcPageUtil.deleteBigBrackets(inUrls[i]));
							}
							results.add(mUrls[i]);
						}
					}
					if(params.keySet().size()==results.size()){
						routeInfo.setParams(params);
						map.put("isRouteInfos",true);
						map.put("routeInfo",routeInfo);
						return map;
					}	
				}
			}
			
		}
		map.put("isRouteInfos",false);
		map.put("routeInfo",null);
		return map;
	}
	
	@SuppressWarnings("static-access")
	public Object methodInvoke(RouteInfo routeInfo, boolean isSupportSpring)
			throws Exception {
		// TODO Auto-generated method stub
		MvcPageContextUtil easyPageContext = new MvcPageContextUtil();
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
			}
			if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
				if(isMultipartRequest){
					putParams = multipartParams;
				}else{
					putParams = getPutParams(request,charset);
				}
			}
			Class<?> clazz = MvcPageUtil.getClassByClassLoader(MvcPageUtil.getClassLoader(),routeInfo.getClazz());
			Method[] mds = routeInfo.getMethods();
			
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
							if(pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&!isMultipartRequest){
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j]=putParams;
								}else{
								     objs[j]=request.getParameterMap();
								}
								
							}else if(!pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&isMultipartRequest){
								objs[j] = MvcPageUtil.createObjectByParamType(pmType,multipartParams.get(param.value()));
							}else if(!pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&!isMultipartRequest){
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,putParams.get(param.value()));
								}else{
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,request.getParameter(param.value()));
								}
							}
						}else if("QueryParam".equals(annotation.annotationType().getSimpleName())){
							QueryParam param = (QueryParam) annotation;
							String pmType = mdTypes[j].toString();
							if(pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&!isMultipartRequest){
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j]=putParams;
								}else{
								     objs[j]=request.getParameterMap();
								}				
							}else if(!pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&isMultipartRequest){
								objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(multipartParams.get(param.value()),charset));
							}else if(!pmType.equals("java.util.Map<java.lang.String, java.lang.Object[]>")&&!isMultipartRequest){
								if("put".equalsIgnoreCase(method) || "delete".equalsIgnoreCase(method)){
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(putParams.get(param.value()),charset));
								}else{
									objs[j] = MvcPageUtil.createObjectByParamType(pmType,MvcPageUtil.urlDecoder(request.getParameter(param.value()),charset));
								}
							}
						}else if("RouteParam".equals(annotation.annotationType().getSimpleName())){
							//RouteParam param = (RouteParam) annotation;
							//String val = MvcPageUtil.getMapValue(routeInfo.getParams(),MvcPageUtil.addBigBrackets(param.value()));
							String pmType = mdTypes[j].toString();
							if(pmType.equals("java.util.Map<java.lang.String, java.lang.Object>")){
								objs[j] =routeInfo.getParams() ;
								
							}else{
								throw new Exception("RouteParam时，必须Map作为参数");
							}
							
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
					Class<?> interceptorclass=routeInfo.getInterceptor();
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
