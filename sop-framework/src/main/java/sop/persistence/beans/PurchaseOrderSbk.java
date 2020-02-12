package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:01
 * @Package: sop.persistence.beans
 */

public class PurchaseOrderSbk extends BaseBean {

    private static final long serialVersionUID = -185248557316796065L;

    private Date po3ExpShpDate;

    private BigDecimal po3ExpShpQty;

    private String po3No;

    private String po3Remk;

    private Integer id;

    public PurchaseOrderSbk() {

    }

    public PurchaseOrderSbk(SaleOrderSbk saleOrderSbk) {
        this.po3ExpShpDate = saleOrderSbk.getSo3ExpShpDate();
        this.po3ExpShpQty = saleOrderSbk.getSo3ExpShpQty();
        this.po3Remk = saleOrderSbk.getSo3Remk();
    }

    public Date getPo3ExpShpDate() {
        return po3ExpShpDate;
    }

    public void setPo3ExpShpDate(Date po3ExpShpDate) {
        this.po3ExpShpDate = po3ExpShpDate;
    }

    public BigDecimal getPo3ExpShpQty() {
        return po3ExpShpQty;
    }

    public void setPo3ExpShpQty(BigDecimal po3ExpShpQty) {
        this.po3ExpShpQty = po3ExpShpQty;
    }

    public String getPo3No() {
        return po3No;
    }

    public void setPo3No(String po3No) {
        this.po3No = po3No;
    }

    public String getPo3Remk() {
        return po3Remk;
    }

    public void setPo3Remk(String po3Remk) {
        this.po3Remk = po3Remk;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
