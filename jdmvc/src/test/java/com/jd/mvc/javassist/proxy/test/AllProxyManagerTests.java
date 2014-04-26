
package com.jd.mvc.javassist.proxy.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({BasicProxyFactoryTestCase.class,
    PrimitiveProxyFactoryCalledByWrapperTestCase.class, 
    PrimitiveProxyFactoryCalledByHandlerTestCase.class,
    BoxedProxyFactoryCalledByWrapperTestCase.class,
    BoxedProxyFactoryCalledByHandlerTestCase.class,
    PrimitiveArrayProxyFactoryCalledByHandlerTestCase.class,
    
    BoxedArrayProxyFactoryCalledByHandlerTestCase.class,
    ProxyAndFilterTestCase.class})
@RunWith(Suite.class)
public class AllProxyManagerTests {

}
