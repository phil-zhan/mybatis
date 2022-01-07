### `MapperProxyFactory` 用于创建 `MapperProxy` 代理

### `MapperProxy` 在具体的接口方法被调用的时候，会创建一个 `MapperProxy` 代理。调用里面的 `invoke()` 方法去执行

### `MapperMethod` 是一个接口到sql(mapper)的映射.两个重要的属性 `SqlCommand` SQL的类型(增删改查) 和 `MethodSignature` 用于记录方法的返回值类型和参数名称解析器

### `BindingException` 对绑定异常的封装