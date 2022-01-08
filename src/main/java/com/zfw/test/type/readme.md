# 类型的转换处理器，见 `org.apache.ibatis.type` 包下
在进行具体的操作之前，不知道要用到哪些类型转换器，就先把可能的类型转换器都准备好，放到一个抽象类的集合中，利用责任链等方式串行处理  
这里利用的是 `TypeHandlerRegistry`  
### Alias
除了类型转换之外，`Alias` 别名也是类似的操作  
当然，除了链式处理之外，还可以先放在集合了，需要的时候get出来    
```
  // 记录了全部TypeHandler的类型以及该类型相关的TypeHandler对象
  // 需要处理什么类型的时候，直接通过class就可以get出来
  private final Map<Class<?>, TypeHandler<?>> allTypeHandlersMap = new HashMap<>();
  
```


### 参数转换器的用处
在解析SQL占位符的时候，会调用 `DefaultParameterHandler` 里面的方法去处理。
在结果集的映射的时候，会调用 `DefaultResultSetHandler` 里面的方法，对结果集解析处理。