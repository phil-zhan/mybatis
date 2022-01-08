package com.zfw.dao;

import com.zfw.bean.Salgrade;

public interface SalgradeDao {
    int deleteByPrimaryKey(Integer deptno);

    int insert(Salgrade record);

    int insertSelective(Salgrade record);

    Salgrade selectByPrimaryKey(Integer deptno);

    int updateByPrimaryKeySelective(Salgrade record);

    int updateByPrimaryKey(Salgrade record);
}