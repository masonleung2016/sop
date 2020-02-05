package sop.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:52
 * @Package: sop.persistence.beans
 */

public class Order extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private String orderNum;
    
    private Date importdatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getImportdatetime() {
        return importdatetime;
    }

    public void setImportdatetime(Date importdatetime) {
        this.importdatetime = importdatetime;
    }
}
