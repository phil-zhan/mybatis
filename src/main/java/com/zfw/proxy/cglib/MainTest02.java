package com.zfw.proxy.cglib;


import net.sf.cglib.proxy.Enhancer;

/**
 * @author phil
 * @date 2022/1/3 12:14
 */
public class MainTest02 {
  public static void main(String[] args) {

    // 通过cglib动态代理获取代理对象的过程
    // 创建调用对象
    // 在后续创建代理过程中，需要用到一个 EnhancerKey 对象，
    // 所以在 Enhancer 实例化的时候就会去创建这个 EnhancerKey 对象。提前准备好
    // EnhancerKey 对象中有一个 newInstance() 方法。恰巧这个 EnhancerKey 对象也需要用代理来生成
    // 这个new Enhancer()的过程，处理初始化一些属性之外，还会执行一些静态static代码块。
    Enhancer enhancer = new Enhancer();

    // 设置 enhancer对象的父类,也就是被代理类
    enhancer.setSuperclass(MyCalculator1.class);

    // 设置enhancer的回调对象
    enhancer.setCallback(new MyCglib());

    // 创建代理对象。其实拿到的是一个 MyCalculator 的子类。对应的所有方法已经被重写
    // 当调用对应的方法时，都会去调回调函数设置的 intercept () 方法
    // intercept（） 方法处理实现代理逻辑之外，还会去调 methodProxy.invokeSuper(o,objects)，
    // 完成父类（被代理类）方法的真正调用
    MyCalculator1 myCalculator = (MyCalculator1)enhancer.create();

    // 通过代理对象调用目标方法
    int add = myCalculator.add(1, 1);

    System.out.println(myCalculator.getClass());

  }
}
