package com.zfw.test;

import com.zfw.bean.Emp;
import com.zfw.dao.EmpDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author phil
 * @date 2022/1/7 23:37
 */
@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MyMybatisSpringTest {

    @Autowired
    private EmpDao empDao;

    @Test
    public void test(){
        List<Emp> empList = empDao.selectAll();
        System.out.println(empList);
    }
}
