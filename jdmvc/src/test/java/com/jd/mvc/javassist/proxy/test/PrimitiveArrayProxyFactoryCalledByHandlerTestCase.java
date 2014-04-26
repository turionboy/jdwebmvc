package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.HandlerCallingTarget;
import com.jd.mvc.javassist.proxy.support.PrimitiveArrayClass;


public class PrimitiveArrayProxyFactoryCalledByHandlerTestCase {
    
    @Test
    public void testBooleanMethod() throws Exception {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        boolean[] b = new boolean[] {true, false, true};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {b});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
        
        assertEquals(b, proxy.testBooleanArray(new boolean[] {true, true}));
        assertEquals("testBooleanArray", handler.m.getName());
    }
    
    @Test
    public void testByteMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        byte[] b = new byte[] {1, 2, 3};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {b});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
    
        assertEquals(b, proxy.testByteArray(new byte[] {5,6,7}));
        assertEquals("testByteArray", handler.m.getName());
    }
    
    @Test
    public void testCharMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        char[] c = new char[] {'a', 'b'};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {c});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
        
        assertEquals(c, proxy.testCharArray(new char[] {'d', 'e'}));
        assertEquals("testCharArray", handler.m.getName());
    }
    
    @Test
    public void testDoubleMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        double[] d = new double[] {1d, 2d};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {d});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
    
        
        assertEquals(d, proxy.testDoubleArray(new double[] {5d, 6d}));
        assertEquals("testDoubleArray", handler.m.getName());
    }
    
    @Test
    public void testFloatMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        float[] f = new float[] {1.0f, 2.0f};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {f});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
        
        assertEquals(f, proxy.testFloatArray(new float[] {6.f}));
        assertEquals("testFloatArray", handler.m.getName());
    }
    
    @Test
    public void testIntMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        int[] i = new int[] {1, 2};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {i});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
    
        assertEquals(i, proxy.testIntArray(new int[] {6, 7, 8}));
        assertEquals("testIntArray", handler.m.getName());
    }
    
    @Test
    public void testLongMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        long[] l = new long[] {123L, 234L};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {l});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
     
        assertEquals(l, proxy.testLongArray(new long[] {8777L}));
        assertEquals("testLongArray", handler.m.getName());
    }
    
    @Test
    public void testShortMethod() {
        PrimitiveArrayClass target = new PrimitiveArrayClass();
        short[] s = new short[] {3, 4};
        HandlerCallingTarget<PrimitiveArrayClass> handler = new HandlerCallingTarget<PrimitiveArrayClass>(target, new Object[] {s});
        PrimitiveArrayClass proxy = ProxyFactory.createProxy(PrimitiveArrayClass.class, handler);
     
        assertEquals(s, proxy.testShortArray(new short[] {6, 7}));
        assertEquals("testShortArray", handler.m.getName());
    }
}
