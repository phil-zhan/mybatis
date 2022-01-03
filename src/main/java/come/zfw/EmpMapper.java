package come.zfw;

import come.zfw.bean.Emp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmpMapper {

  @Select("select * from emp where empno = #{empNo} and ename =#{ename}")
  List<Emp> selectmpList(Integer empNo, String ename);
}
