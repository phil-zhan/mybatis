package com.zfw.test.enum1;

/**
 * @author phil
 * @date 2022/1/3 23:26
 */
public class MainTest {

    public static void main(String[] args) {
        EnumTest spring = EnumTest.valueOf("SPRING");
        EnumTest spring1 = EnumTest.SPRING;
        System.out.println(spring == spring1);
    }
}
