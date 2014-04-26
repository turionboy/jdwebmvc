
package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.BoxedArrayClass;
import com.jd.mvc.javassist.proxy.support.HandlerCallingTarget;

public class BoxedArrayProxyFactoryCalledByHandlerTestCase {
    
    @Test
    public void testBooleanMethod() throws Exception {
        BoxedArrayClass target = new BoxedArrayClass();
        Boolean[] b = new Boolean[] {true, false, true};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {b});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
        
        assertEquals(b, proxy.testBooleanArray(new Boolean[] {true, true}));
        assertEquals("testBooleanArray", handler.m.getName());
    }
    
    @Test
    public void testByteMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Byte[] b = new Byte[] {1, 2, 3};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {b});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
    
        assertEquals(b, proxy.testByteArray(new Byte[] {3, 2, 1}));
        assertEquals("testByteArray", handler.m.getName());
    }
    
    @Test
    public void testCharMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Character[] c = new Character[] {'a', 'b'};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {c});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
        
        assertEquals(c, proxy.testCharArray(new Character[] {'f', 'g'}));
        assertEquals("testCharArray", handler.m.getName());
    }
    
    @Test
    public void testDoubleMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Double[] d = new Double[] {1d, 2d};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {d});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
    
        assertEquals(d, proxy.testDoubleArray(new Double[] {5d, 3d}));
        assertEquals("testDoubleArray", handler.m.getName());
    }
    
    @Test
    public void testFloatMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Float[] f = new Float[] {1.0f, 2.0f};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {f});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
        
        assertEquals(f, proxy.testFloatArray(new Float[] {4f, 5f}));
        assertEquals("testFloatArray", handler.m.getName());
    }
    
    @Test
    public void testIntMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Integer[] i = new Integer[] {1, 2};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {i});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
    
        assertEquals(i, proxy.testIntArray(new Integer[] {5, 6}));
        assertEquals("testIntArray", handler.m.getName());
    }
    
    @Test
    public void testLongMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Long[] l = new Long[] {123L, 234L};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {l});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
     
        assertEquals(l, proxy.testLongArray(new Long[] {555L, 444L}));
        assertEquals("testLongArray", handler.m.getName());
    }
    
    @Test
    public void testShortMethod() {
        BoxedArrayClass target = new BoxedArrayClass();
        Short[] s = new Short[] {3, 4};
        HandlerCallingTarget<BoxedArrayClass> handler = new HandlerCallingTarget<BoxedArrayClass>(target, new Object[] {s});
        BoxedArrayClass proxy = ProxyFactory.createProxy(BoxedArrayClass.class, handler);
     
        assertEquals(s, proxy.testShortArray(new Short[] {4, 5}));
        assertEquals("testShortArray", handler.m.getName());
    }
}
