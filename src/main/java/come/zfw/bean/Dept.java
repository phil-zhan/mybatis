package come.zfw.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * dept
 * @author phil
 */
@Data
public class Dept implements Serializable {
    /**
     * 部门编号
     */
    private Integer deptno;

    private String dname;

    private String loc;

    private static final long serialVersionUID = 1L;
}