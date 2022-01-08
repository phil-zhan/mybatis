package com.zfw;

import com.zfw.bean.Emp;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMybatis {

    public static void main(String[] args) {

        EmpMapper empMapper = (EmpMapper) Proxy.newProxyInstance(MyMybatis.class.getClassLoader(), new Class[]{EmpMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                // 通过参数来完成替换功能。因此需要先去解析参数
                // 如果出现参数名称获取不一样，可以在 Java Compiler 上加参数  -parameters
                Map<String, Object> stringObjectMap = parseParam(method, objects);

                // 获取方法上的注解
                Select select = method.getAnnotation(Select.class);
                if (null != select) {
                    // 核心逻辑处理
                    String[] value = select.value();
                    String sql = value[0];
                    sql = parseSql2(sql, stringObjectMap);
                    System.out.println(sql);
                }

                // 数据库操作

                // 结果集的映射处理

                return null;
            }
        });

        List<Emp> empList = empMapper.selectmpList(7369, "SMITH");
    }

    public static String parseSql(String sql, Map<String, Object> paramMap) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '#') {
                int index = i + 1;
                char nextChar = sql.charAt(index);
                if(nextChar != '{'){
                    throw new RuntimeException("Sql 语句有错误，不是以{开头的");
                }

                // builder.append(sql.substring(0,i));
                StringBuilder argStringBuilder = new StringBuilder();
                i = parseSqlParam(argStringBuilder,sql,index);
                String argName = argStringBuilder.toString();
                Object value = paramMap.get(argName);

                builder.append("'").append(value.toString()).append("'");
                continue;

            }

            builder.append(c);
        }

        return builder.toString();
    }

    public static int parseSqlParam(StringBuilder argStringBuilder,String sql,int index){
        index++;

        for (; index < sql.length(); index++) {
            char c = sql.charAt(index);
            if(c != '}'){
                argStringBuilder.append(c);
                continue;
            }
            return index;
        }

        throw new RuntimeException("SQL语句错误，参数没有以'}' 结尾");
    }

    public static Map<String, Object> parseParam(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        // 获取方法的参数
        int[] index = {0};
        Parameter[] parameters = method.getParameters();
        Arrays.asList(parameters).forEach(parameter -> {
            map.put(parameter.getName(), args[index[0]]);
            index[0]++;
        });

        return map;

    }

    public static String parseSql2(String sql,Map<String,Object> paramMap){

        for (Map.Entry<String,Object> entry:paramMap.entrySet()) {
            sql = sql.replace("#{" + entry.getKey() + "}", "'" + entry.getValue() + "'");
        }

        return sql;
    }
}
