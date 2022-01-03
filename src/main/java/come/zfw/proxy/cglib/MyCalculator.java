package come.zfw.proxy.cglib;

/**
 * 被代理类
 * @author phil
 * @date 2020/12/10 6:57
 */

public class MyCalculator implements Calculator{
  public int add(int i, int j) {
    return i+j;
  }

  public int sub(int i, int j) {
    return i-j;
  }

  public int mult(int i, int j) {
    return i*j;
  }

  public int div(int i, int j) {
    return i/j;
  }
}
