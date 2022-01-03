package come.zfw.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 测试jdk代理
 * @date 2022-01-03 11:51:28
 */
public class TestMain01 {

  public static void main(String[] args) {
    Calculator calculator = (Calculator)Proxy.newProxyInstance(Calculator.class.getClassLoader(), new Class[]{Calculator.class}, (object, method, args1) -> {
      Object res;
      // 也就是在方法执行的时候，修改其执行的对象
      res = method.invoke(new MyCalculator(), args1);
      return res;
    });

    int resAdd = calculator.add(1, 2);
    System.out.println(resAdd);
  }
}
