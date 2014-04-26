package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import org.junit.Test;

import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.BoxedClass;
import com.jd.mvc.javassist.proxy.support.HandlerCallingTarget;

public class BoxedProxyFactoryCalledByHandlerTestCase {
    
    @Test
    public void testBooleanMethod() throws Exception {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {false});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertFalse(proxy.testBoolean(true));
        assertEquals("testBoolean", handler.m.getName());
    }
    
    @Test
    public void testByteMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {(byte)6});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals((byte)6, proxy.testByte((byte)2).byteValue());
        assertEquals("testByte", handler.m.getName());
    }
    
    @Test
    public void testCharMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {'d'});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals('d', proxy.testChar('a').charValue());
        assertEquals("testChar", handler.m.getName());
    }
    
    @Test
    public void testDoubleMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {765d});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(765d, proxy.testDouble(457.0d).doubleValue());
        assertEquals("testDouble", handler.m.getName());
    }
    
    @Test
    public void testFloatMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {8f});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(8f, proxy.testFloat(6.0f).floatValue());
        assertEquals("testFloat", handler.m.getName());
    }
    
    @Test
    public void testIntMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {321});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(321, proxy.testInt(123).intValue());
        assertEquals("testInt", handler.m.getName());
    }
    
    @Test
    public void testLongMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {99999L});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(99999L, proxy.testLong(12399L).longValue());
        assertEquals("testLong", handler.m.getName());
    }
    
    @Test
    public void testShortMethod() {
        BoxedClass target = new BoxedClass();
        HandlerCallingTarget<BoxedClass> handler = new HandlerCallingTarget<BoxedClass>(target, new Object[] {(short)98});
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(98, proxy.testShort((short)78).shortValue());
        assertEquals("testShort", handler.m.getName());
    }
}
