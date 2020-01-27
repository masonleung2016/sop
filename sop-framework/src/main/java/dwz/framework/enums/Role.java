package dwz.framework.enums;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:39
 * @Package: dwz.framework.enums
 */

public enum Role {
    admin("管理员"), normalUser("普通用户");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
