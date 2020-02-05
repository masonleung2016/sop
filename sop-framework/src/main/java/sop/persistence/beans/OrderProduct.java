package sop.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:53
 * @Package: sop.persistence.beans
 */

public class OrderProduct extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private Integer fkOrder;
    
    private String productNo;
    
    private String productEngName;
    
    private String productChnName;
    
    private Integer fkProduct;
    
    private Date importdatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkOrder() {
        return fkOrder;
    }

    public void setFkOrder(Integer fkOrder) {
        this.fkOrder = fkOrder;
    }

    public Integer getFkProduct() {
        return fkProduct;
    }

    public void setFkProduct(Integer fkProduct) {
        this.fkProduct = fkProduct;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductEngName() {
        return productEngName;
    }

    public void setProductEngName(String productEngName) {
        this.productEngName = productEngName;
    }

    public String getProductChnName() {
        return productChnName;
    }

    public void setProductChnName(String productChnName) {
        this.productChnName = productChnName;
    }

    public Date getImportdatetime() {
        return importdatetime;
    }

    public void setImportdatetime(Date importdatetime) {
        this.importdatetime = importdatetime;
    }
}
