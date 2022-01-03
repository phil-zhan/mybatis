package come.zfw.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * 其实是定义了一个回调方法,完全可以在使用的时候直接new 一个MethodInterceptor，然后重写intercept方法
 * @author phil
 * @date 2022/1/3 12:09
 */

public class MyCglib implements MethodInterceptor {

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

    return 1;
    //return methodProxy.invokeSuper(o,objects);
  }
}
