package com.zfw.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * emp
 * @author 
 */
@Data
public class Emp implements Serializable {
    private Integer empno;

    private String ename;

    private String job;

    private Integer mgr;

    private Date hiredate;

    private Integer sal;

    private Integer comm;

    private Integer deptno;

    private static final long serialVersionUID = 1L;

    public Emp(Integer empno, String ename) {
        this.empno = empno;
        this.ename = ename;
    }
}