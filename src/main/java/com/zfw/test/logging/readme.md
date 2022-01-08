# 日志相关类
### 里面有对不同日志框架的实现
### 可以根据配置来选用合适的实现输出日志

```xml
 <settings>
    <!-- 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。 -->
    <!-- SLF4J | LOG4J(deprecated since 3.5.9) | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING | NO_LOGGING -->
    <!-- 这里的value使用简写，原因在于创建configuration全局对象的时候，注册了所有的别名，在需要使用的时候，可以通过别名找到具体的实现类 -->
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```