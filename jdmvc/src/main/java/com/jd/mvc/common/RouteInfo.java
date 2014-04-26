package com.jd.mvc.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class RouteInfo implements Serializable{
	private String route;
	private int routeLength;
	private String clazz;
	private String method;
	private String callMethod;
	private Object[] objs;
	private List<Map<String,String>> params;
	
	public RouteInfo(){
		
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public int getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(int routeLength) {
		this.routeLength = routeLength;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

	public List<Map<String, String>> getParams() {
		return params;
	}

	public void setParams(List<Map<String, String>> params) {
		this.params = params;
	}

	public String getCallMethod() {
		return callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}
	
}
