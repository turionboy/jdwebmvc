
package com.jd.mvc.javassist.proxy.support;


public class ClassWithInnerClasses {

    public static class StaticClass{
        public void method() {
            
        }
    }
    
    public class NonStaticClass{
        public void method() {
            
        }
    }
    
    public NonStaticClass getNonStaticInstance() {
        return new NonStaticClass();
    }
}
