package come.zfw.dao;

import come.zfw.bean.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpDao {
    int deleteByPrimaryKey(Integer empno);

    int insert(Emp record);

    int insertSelective(Emp record);

    Emp selectByPrimaryKey(Integer empno);

    int updateByPrimaryKeySelective(Emp record);

    int updateByPrimaryKey(Emp record);

    Emp findEmpByEmpno(int empNo);

    List<Emp> selectAll();

    Emp findEmpByEmpnoAndEname(@Param("empno") Integer empno, @Param("ename") String ename);
}