package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:07
 * @Package: sop.persistence.beans
 */

public class SaleOrderSbk extends BaseBean {

    private static final long serialVersionUID = -185248557316796065L;
    
    private Integer id;

    private String coCode;

    private String so3No;

    private Date so3ExpShpDate;

    private BigDecimal so3ExpShpQty;

    private String so3Remk;
    
    public SaleOrderSbk() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getSo3No() {
        return so3No;
    }

    public void setSo3No(String so3No) {
        this.so3No = so3No;
    }

    public Date getSo3ExpShpDate() {
        return so3ExpShpDate;
    }

    public void setSo3ExpShpDate(Date so3ExpShpDate) {
        this.so3ExpShpDate = so3ExpShpDate;
    }

    public BigDecimal getSo3ExpShpQty() {
        return so3ExpShpQty;
    }

    public void setSo3ExpShpQty(BigDecimal so3ExpShpQty) {
        this.so3ExpShpQty = so3ExpShpQty;
    }

    public String getSo3Remk() {
        return so3Remk;
    }

    public void setSo3Remk(String so3Remk) {
        this.so3Remk = so3Remk;
    }
}
