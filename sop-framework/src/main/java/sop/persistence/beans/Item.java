package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:49
 * @Package: sop.persistence.beans
 */

public class Item {

    private String id;

    private String cat;

    private String no;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
