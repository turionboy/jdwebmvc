
package com.jd.mvc.javassist.proxy.support;

public class ChildClass extends ParentClass{
    
    @Override
    public String overridden(int i, int j) {
        return String.valueOf(i);
    }
}
