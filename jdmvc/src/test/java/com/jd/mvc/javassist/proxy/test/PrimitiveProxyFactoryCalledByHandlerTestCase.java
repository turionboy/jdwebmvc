package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import org.junit.Test;
import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.HandlerCallingTarget;
import com.jd.mvc.javassist.proxy.support.PrimitiveClass;


public class PrimitiveProxyFactoryCalledByHandlerTestCase {
    
    @Test
    public void testStringMethod() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {"Replaced"});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals("Replaced", proxy.testString("Test"));
        assertEquals("testString", handler.m.getName());
    }
    
    @Test
    public void testBooleanMethod() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {false});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertFalse(proxy.testBoolean(true));
        assertEquals("testBoolean", handler.m.getName());
    }
    
    @Test
    public void testByteMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {(byte)5});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals((byte)5, proxy.testByte((byte)2));
        assertEquals("testByte", handler.m.getName());
    }
    
    @Test
    public void testCharMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {'f'});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals('f', proxy.testChar('a'));
        assertEquals("testChar", handler.m.getName());
    }
    
    @Test
    public void testDoubleMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {123.0d});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(123.0d, proxy.testDouble(457.0d));
        assertEquals("testDouble", handler.m.getName());
    }
    
    @Test
    public void testFloatMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {11.0f});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(11.0f, proxy.testFloat(6.0f));
        assertEquals("testFloat", handler.m.getName());
    }
    
    @Test
    public void testIntMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {66});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(66, proxy.testInt(123));
        assertEquals("testInt", handler.m.getName());
    }
    
    @Test
    public void testLongMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {99999L});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(99999L, proxy.testLong(12399L));
        assertEquals("testLong", handler.m.getName());
    }
    
    @Test
    public void testShortMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerCallingTarget<PrimitiveClass> handler = new HandlerCallingTarget<PrimitiveClass>(target, new Object[] {(short)34});
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals((short)34, proxy.testShort((short)78));
        assertEquals("testShort", handler.m.getName());
    }
}
