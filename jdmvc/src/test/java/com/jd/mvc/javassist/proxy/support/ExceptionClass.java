
package com.jd.mvc.javassist.proxy.support;


public class ExceptionClass {
	public void raiseCheckedException() throws CheckedException{
		throw new CheckedException();
	}
	
	public void raiseUncheckedException() {
		throw new UncheckedException();
	}
}
