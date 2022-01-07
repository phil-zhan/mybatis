package come.zfw.test.reflector;

import org.apache.ibatis.reflection.*;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class MainTestReflector {

  @Test
  public void test01(){
    Reflector reflector = new Reflector(Person.class);
    System.out.println(reflector);
  }

  @Test
  public void test02(){

    // 利用工厂的目的是 工厂加入了缓存机制，可以避免重复创建反射器
    ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    Reflector reflector = reflectorFactory.findForClass(Student.class);


    System.out.println("可get属性列表如下========================");
    System.out.println(Arrays.asList(reflector.getGetablePropertyNames()));

    System.out.println("可set属性列表如下========================");
    System.out.println(Arrays.asList(reflector.getSetablePropertyNames()));

    System.out.println("是否有默认构造参数========================");
    System.out.println(reflector.hasDefaultConstructor());

    System.out.println("get对应的类型========================");
    System.out.println(reflector.getGetterType("id"));
  }

  /**
   * Invoker 工具类的使用
   * @date 2022-01-06 08:13:34
   */
  @Test
  public void test03() throws Exception {

    Class<Student> clazz = Student.class;
    // 反射1
    Student student = clazz.newInstance();

    // 反射2【默认构造器实现】
    Constructor<Student> declaredConstructor = clazz.getDeclaredConstructor();
    Student student1 = declaredConstructor.newInstance();

    // 反射3【默认构造器实现】
    ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    Reflector reflector = reflectorFactory.findForClass(Student.class);


    Object obj = reflector.getDefaultConstructor().newInstance();
    //设置
    Invoker invoker = reflector.getSetInvoker("id");
    invoker.invoke(obj,new Object[]{1111});

    // 获取
    Invoker id = reflector.getGetInvoker("id");
    id.invoke(obj,null);
  }

  @Test
  public void test04(){

    ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    MetaClass metaClass = MetaClass.forClass(User.class, reflectorFactory);
    System.out.println(metaClass.hasGetter("userField"));
    Class<?> clazz = metaClass.getSetterType("user");
    Class<?> clazz1 = metaClass.getGetterType("user");



    System.out.println(metaClass.hasGetter("userProperty"));
    System.out.println(metaClass.hasGetter("userMap"));
    System.out.println(metaClass.hasGetter("user"));
    System.out.println(metaClass.hasGetter("userlist"));
    System.out.println(metaClass.hasGetter("userlist[0]"));
    System.out.println("-----------");
    System.out.println(metaClass.hasGetter("user.userField"));
    System.out.println(metaClass.hasGetter("user.userProperty"));
    System.out.println(metaClass.hasGetter("user.userMap"));
    System.out.println(metaClass.hasGetter("user.userlist"));
    System.out.println(metaClass.hasGetter("user.user"));
    System.out.println("------------");
    System.out.println(Arrays.asList(metaClass.getGetterNames()));
    System.out.println(Arrays.asList(metaClass.getSetterNames()));
  }

  @Test
  public void test05(){
    User user = new User();
    MetaObject metaObject = SystemMetaObject.forObject(user);
    metaObject.setValue("userField","lian");
    System.out.println(metaObject.getValue("userField"));

    // 复杂属性，需要对应的属性对象有默认无参构造方法.  get/set方法可有可无
    metaObject.setValue("user.userField","lian");
    System.out.println(metaObject.getValue("user.userField"));

    metaObject.setValue("userMap[key]","lian");
    System.out.println(metaObject.getValue("userMap[key]"));

    metaObject.setValue("person.id",111);
    System.out.println(metaObject.getValue("person.id"));
  }
}
