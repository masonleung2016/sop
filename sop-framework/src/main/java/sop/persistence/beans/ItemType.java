package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:50
 * @Package: sop.persistence.beans
 */

public class ItemType extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
