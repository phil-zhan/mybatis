package come.zfw.proxy.jdk;

/**
 * @author phil
 * @date 2022/1/3 11:59
 */
public class MyCalculator2 implements Calculator{

  @Override
  public int add(int a, int b) {
    return a+b;
  }

  @Override
  public int sub(int a, int b) {
    return a-b;
  }

  @Override
  public int mult(int a, int b) {
    return a*b;
  }

  @Override
  public int div(int a, int b) {
    return a/b;
  }
}
