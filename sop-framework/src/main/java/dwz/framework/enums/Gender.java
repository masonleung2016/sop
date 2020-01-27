package dwz.framework.enums;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:39
 * @Package: dwz.framework.enums
 */

public enum Gender {
    male("男"), female("女");

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
