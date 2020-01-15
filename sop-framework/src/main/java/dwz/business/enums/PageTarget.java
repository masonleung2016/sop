package dwz.business.enums;

/**
 * @Author: LCF
 * @Date: 2020/1/8 11:39
 * @Package: dwz.business.enums
 */

public enum PageTarget {
    header("Header"),
    //	sidebar("Sidebar"),
    footer("Footer");

    private String name;

    PageTarget(String name) {
        this.name = name;
    }

    public String getId() {
        return this.toString();
    }

    public String getName() {
        return this.name;
    }
}
