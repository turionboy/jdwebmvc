
package com.jd.mvc.javassist.proxy.support;


public class CornerCaseClass {
    
    public static void staticMethod() {
        
    }
    
    public final void finalMethod() {
        
    }
    
    @SuppressWarnings("unused")
    private void privateMethod() {
        
    }
    
    protected void protectedMethod() {
        
    }
    
    void packageProtectedMethod() {
        
    }
    
    public String mixedParameters(int i, double d, float f, long l, short s) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("-");
        sb.append(Math.round(d));
        sb.append("-");
        sb.append(Math.round(f));
        sb.append("-");
        sb.append(l);
        sb.append("-");
        sb.append(s);
        return sb.toString();
    }
}
