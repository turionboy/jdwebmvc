
package com.jd.mvc.javassist.proxy.support;


public class ParentClass {
    public Object overridden(int i, int j) {
        return new Integer(i);
    }
}
