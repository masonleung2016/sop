package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:47
 * @Package: sop.persistence.beans
 */

public class Employee {

    public Integer id;
    public String name;

    public Employee(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

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
