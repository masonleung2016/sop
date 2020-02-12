package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:08
 * @Package: sop.persistence.beans
 */

public class Staff extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private String sfCode;
    
    private String sfName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSfCode() {
        return sfCode;
    }

    public void setSfCode(String sfCode) {
        this.sfCode = sfCode;
    }

    public String getSfName() {
        return sfName;
    }

    public void setSfName(String sfName) {
        this.sfName = sfName;
    }
}
