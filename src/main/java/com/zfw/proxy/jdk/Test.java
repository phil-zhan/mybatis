package com.zfw.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author phil
 * @date 2022/1/5 22:49
 */
public class Test implements InvocationHandler {

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return null;
    }
}
