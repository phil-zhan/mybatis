package come.zfw.test.enum1;

public enum EnumTest {

    SPRING("春天",1),
    WARM("",2),
    FAIL("",3),
    A1("2",3);

    private String name;
    private int code;
    EnumTest(String name, int code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
