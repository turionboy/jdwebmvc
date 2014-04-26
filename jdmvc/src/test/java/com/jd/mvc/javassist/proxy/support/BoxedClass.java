
package com.jd.mvc.javassist.proxy.support;



public class BoxedClass implements IBox{

    public Boolean testBoolean(Boolean b) {
        return b;
    }
    
    public Byte testByte(Byte b) {
        return b;
    }
    
    public Character testChar(Character c) {
        return c;
    }
    
    public Double testDouble(Double d) {
        return d;
    }
    
    public Float testFloat(Float f) {
        return f;
    }
    
    public Integer testInt(Integer i) {
        return i;
    }
    
    public Long testLong(Long l) {
        return l;
    }
    
    public Short testShort(Short s) {
        return s;
    }
}
