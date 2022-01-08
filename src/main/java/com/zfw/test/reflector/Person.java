package com.zfw.test.reflector;



/**
 * 该类只有属性没有方法
 * @date 2022-01-06 07:55:26
 */
public class Person {


  private Integer id;
  private String name;

  public Person(){}
  public Person(Integer id){
    this.id = id;
  }

  public Person(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}
