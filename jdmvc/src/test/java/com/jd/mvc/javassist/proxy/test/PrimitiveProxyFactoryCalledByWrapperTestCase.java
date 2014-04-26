package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.HandlerNotCallingTarget;
import com.jd.mvc.javassist.proxy.support.PrimitiveClass;

public class PrimitiveProxyFactoryCalledByWrapperTestCase {
    
    @Test
    public void testStringMethod() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals("Test", proxy.testString("Test"));
        assertEquals("testString", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals("Test", handler.args[0]);
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testBooleanMethod() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertTrue(proxy.testBoolean(true));
        assertEquals("testBoolean", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertTrue(((Boolean)handler.args[0]).booleanValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testByteMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals((byte)2, proxy.testByte((byte)2));
        assertEquals("testByte", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals((byte)2, ((Byte)handler.args[0]).byteValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testCharMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals('a', proxy.testChar('a'));
        assertEquals("testChar", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals('a', ((Character)handler.args[0]).charValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testDoubleMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(457.0d, proxy.testDouble(457.0d));
        assertEquals("testDouble", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(457.0d, ((Double)handler.args[0]).doubleValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testFloatMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(6.0f, proxy.testFloat(6.0f));
        assertEquals("testFloat", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(6.0f, ((Float)handler.args[0]).floatValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testIntMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(123, proxy.testInt(123));
        assertEquals("testInt", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(123, ((Integer)handler.args[0]).intValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testLongMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals(12399L, proxy.testLong(12399L));
        assertEquals("testLong", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(12399L, ((Long)handler.args[0]).longValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testShortMethod() {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        assertEquals((short)78, proxy.testShort((short)78));
        assertEquals("testShort", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals((short)78, ((Short)handler.args[0]).shortValue());
        assertSame(target, handler.instance);
    }
}
