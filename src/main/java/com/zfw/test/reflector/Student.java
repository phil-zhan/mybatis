package com.zfw.test.reflector;


/**
 * 该类只有方法没有属性
 * @date 2022-01-06 07:55:26
 */
public class Student {

  public Integer getId(){
    System.out.println("运行get方法");
    return 6;
  }

  public void setId(Integer id){
    System.out.println("运行set方法");
    System.out.println(id);
  }

  public String getName(){
    return "zhangsan";
  }
}
