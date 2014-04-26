package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.BoxedClass;
import com.jd.mvc.javassist.proxy.support.HandlerNotCallingTarget;
import com.jd.mvc.javassist.proxy.support.IBox;


public class BoxedProxyFactoryCalledByWrapperTestCase {
    
    @Test
    public void testBooleanMethod() throws Exception {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertTrue(proxy.testBoolean(true));
        assertEquals("testBoolean", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertTrue(((Boolean)handler.args[0]).booleanValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testByteMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        IBox proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals((byte)2, proxy.testByte((byte)2).byteValue());
        assertEquals("testByte", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals((byte)2, ((Byte)handler.args[0]).byteValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testCharMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals('a', proxy.testChar('a').charValue());
        assertEquals("testChar", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals('a', ((Character)handler.args[0]).charValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testDoubleMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(457.0d, proxy.testDouble(457.0d).doubleValue());
        assertEquals("testDouble", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(457.0d, ((Double)handler.args[0]).doubleValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testFloatMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(6.0f, proxy.testFloat(6.0f).floatValue());
        assertEquals("testFloat", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(6.0f, ((Float)handler.args[0]).floatValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testIntMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(123, proxy.testInt(123).intValue());
        assertEquals("testInt", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(123, ((Integer)handler.args[0]).intValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testLongMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals(12399L, proxy.testLong(12399L).longValue());
        assertEquals("testLong", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals(12399L, ((Long)handler.args[0]).longValue());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testShortMethod() {
        BoxedClass target = new BoxedClass();
        HandlerNotCallingTarget<BoxedClass> handler = new HandlerNotCallingTarget<BoxedClass>(target);
        BoxedClass proxy = ProxyFactory.createProxy(BoxedClass.class, handler);
        
        assertEquals((short)78, proxy.testShort((short)78).shortValue());
        assertEquals("testShort", handler.m.getName());
        assertEquals(1, handler.args.length);
        assertEquals((short)78, ((Short)handler.args[0]).shortValue());
        assertSame(target, handler.instance);
    }
}
