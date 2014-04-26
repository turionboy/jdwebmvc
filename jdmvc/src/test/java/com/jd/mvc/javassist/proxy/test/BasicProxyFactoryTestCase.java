package com.jd.mvc.javassist.proxy.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;


import com.jd.mvc.core.proxy.ProxyFactory;
import com.jd.mvc.javassist.proxy.support.CheckedException;
import com.jd.mvc.javassist.proxy.support.ChildClass;
import com.jd.mvc.javassist.proxy.support.ClassWithInnerClasses;
import com.jd.mvc.javassist.proxy.support.CornerCaseClass;
import com.jd.mvc.javassist.proxy.support.ExceptionClass;
import com.jd.mvc.javassist.proxy.support.FinalClass;
import com.jd.mvc.javassist.proxy.support.HandlerNotCallingTarget;
import com.jd.mvc.javassist.proxy.support.PrimitiveClass;
import com.jd.mvc.javassist.proxy.support.UncheckedException;


public class BasicProxyFactoryTestCase {
    @Test
    public void testProxyFactoryHandlerSetter() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        assertNotNull(proxy);
        assertNotSame(PrimitiveClass.class, proxy.getClass());
        
        Field field = proxy.getClass().getDeclaredField("_proxy$Handler");
        field.setAccessible(true);
        Object o = field.get(proxy);
        assertNotNull(o);
        assertTrue(o instanceof HandlerNotCallingTarget);
        assertSame(handler, o);
    }
    
    @Test
    public void testNoArgsVoidMethod() throws Exception {
        PrimitiveClass target = new PrimitiveClass();
        HandlerNotCallingTarget<PrimitiveClass> handler = new HandlerNotCallingTarget<PrimitiveClass>(target);
        PrimitiveClass proxy = ProxyFactory.createProxy(PrimitiveClass.class, handler);
        
        proxy.test();
        assertEquals("test", handler.m.getName());
        assertEquals(0, handler.args.length);
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testOverriddenMethod() throws Exception {
        ChildClass target = new ChildClass();
        HandlerNotCallingTarget<ChildClass> handler = new HandlerNotCallingTarget<ChildClass>(target);
        ChildClass proxy = ProxyFactory.createProxy(ChildClass.class, handler);
        
        Object ret = proxy.overridden(12, 10);
        assertEquals("overridden", handler.m.getName());
        assertEquals(String.class, handler.m.getReturnType());
        assertEquals(2, handler.args.length);
        assertSame(target, handler.instance);

        assertTrue(ret instanceof String);
        assertEquals("12", ret);
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testStaticMethodNotProxied() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        proxy.staticMethod();
        assertNull(handler.m);
    }

    @Test
    public void testFinalMethodNotProxied() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        proxy.finalMethod();
        assertNull(handler.m);
    }
    
    @Test
    public void testPrivateMethodNotProxied() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        for (Method m : proxy.getClass().getDeclaredMethods()) {
            if (m.getName().equals("privateMethod")) {
                fail("Should not have had private method");
            }
        }
    }

    @Test
    public void testProtectedMethodProxied() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        boolean found = false;
        for (Method m : proxy.getClass().getDeclaredMethods()) {
            if (m.getName().equals("protectedMethod")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }


    @Test
    public void testPackageProtectedMethodProxied() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        boolean found = false;
        for (Method m : proxy.getClass().getDeclaredMethods()) {
            if (m.getName().equals("packageProtectedMethod")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
    
    @Test
    public void testSeveralParametersWithDouhleAndLong() throws Exception {
        CornerCaseClass target = new CornerCaseClass();
        HandlerNotCallingTarget<CornerCaseClass> handler = new HandlerNotCallingTarget<CornerCaseClass>(target);
        CornerCaseClass proxy = ProxyFactory.createProxy(CornerCaseClass.class, handler);
        
        assertEquals("12-34-12-5-3", proxy.mixedParameters(12, 34D, 12f, 5L, (short)3));
        assertEquals("mixedParameters", handler.m.getName());
        assertSame(target, handler.instance);
    }
    
    @Test
    public void testCannotProxyFinalClass() throws Exception {
        FinalClass target = new FinalClass();
        HandlerNotCallingTarget<FinalClass> handler = new HandlerNotCallingTarget<FinalClass>(target);
        try {
            ProxyFactory.createProxy(FinalClass.class, handler);
            fail("Should have had error");
        }catch(IllegalArgumentException expected) {
        }
    }
    
    @Test
    public void testCanProxyStaticInnerClass() throws Exception{
        ClassWithInnerClasses.StaticClass target = new ClassWithInnerClasses.StaticClass();
        HandlerNotCallingTarget<ClassWithInnerClasses.StaticClass> handler = new HandlerNotCallingTarget<ClassWithInnerClasses.StaticClass>(target);
        ClassWithInnerClasses.StaticClass proxy = ProxyFactory.createProxy(ClassWithInnerClasses.StaticClass.class, handler);
        
        proxy.method();
        assertEquals("method", handler.m.getName());
    }
    
    @Test
    public void testCannotProxyNonStaticInnerClass() throws Exception{
        ClassWithInnerClasses.NonStaticClass target = new ClassWithInnerClasses().getNonStaticInstance();
        HandlerNotCallingTarget<ClassWithInnerClasses.NonStaticClass> handler = new HandlerNotCallingTarget<ClassWithInnerClasses.NonStaticClass>(target);
        try {
            ProxyFactory.createProxy(ClassWithInnerClasses.NonStaticClass.class, handler);
            fail("Should have had error");
        }catch (IllegalArgumentException expected) {
        }
    }
    
    @Test
    public void testCheckedException() throws Exception {
        ExceptionClass target = new ExceptionClass();
        HandlerNotCallingTarget<ExceptionClass> handler = new HandlerNotCallingTarget<ExceptionClass>(target);
        ExceptionClass proxy = ProxyFactory.createProxy(ExceptionClass.class, handler);
        
        try {
        	proxy.raiseCheckedException();
        }catch (CheckedException expected) {
        	return;
		}
        fail("Should have had checked exception");
    }
    
    @Test
    public void testUncheckedException() throws Exception {
        ExceptionClass target = new ExceptionClass();
        HandlerNotCallingTarget<ExceptionClass> handler = new HandlerNotCallingTarget<ExceptionClass>(target);
        ExceptionClass proxy = ProxyFactory.createProxy(ExceptionClass.class, handler);
        
        try {
        	proxy.raiseUncheckedException();
        }catch (UncheckedException expected) {
        	return;
		}
        fail("Should have had unchecked exception");
    }
}
