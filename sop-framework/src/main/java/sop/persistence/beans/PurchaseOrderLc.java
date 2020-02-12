package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:01
 * @Package: sop.persistence.beans
 */

public class PurchaseOrderLc extends BaseBean {

    private static final long serialVersionUID = -185248557316796065L;

    private Date po4ExpDate;

    private BigDecimal po4LcAmt;

    private String po4LcNo;

    private String encodePo4LcNo;

    private String po4No;

    private BigDecimal po4PaidAmt;

    private Date po4PaidDate;

    private Integer id;
    
    public PurchaseOrderLc() {

    }

    public PurchaseOrderLc(SaleOrderLc saleOrderLc) {
        this.po4ExpDate = saleOrderLc.getSo4ExpDate();
        this.po4LcAmt = saleOrderLc.getSo4LcAmt();
        this.po4LcNo = saleOrderLc.getSo4LcNo();
        this.encodePo4LcNo = Base64.encode(po4LcNo, "utf-8");
    }

    public Date getPo4ExpDate() {
        return po4ExpDate;
    }

    public void setPo4ExpDate(Date po4ExpDate) {
        this.po4ExpDate = po4ExpDate;
    }

    public BigDecimal getPo4LcAmt() {
        return po4LcAmt;
    }

    public void setPo4LcAmt(BigDecimal po4LcAmt) {
        this.po4LcAmt = po4LcAmt;
    }

    public String getPo4LcNo() {
        return po4LcNo;
    }

    public void setPo4LcNo(String po4LcNo) {
        this.po4LcNo = po4LcNo;
        this.encodePo4LcNo = Base64.encode(po4LcNo, "utf-8");
    }

    public String getPo4No() {
        return po4No;
    }

    public void setPo4No(String po4No) {
        this.po4No = po4No;
    }

    public BigDecimal getPo4PaidAmt() {
        return po4PaidAmt;
    }

    public void setPo4PaidAmt(BigDecimal po4PaidAmt) {
        this.po4PaidAmt = po4PaidAmt;
    }

    public Date getPo4PaidDate() {
        return po4PaidDate;
    }

    public void setPo4PaidDate(Date po4PaidDate) {
        this.po4PaidDate = po4PaidDate;
    }

    public String getEncodePo4LcNo() {
        return encodePo4LcNo;
    }

    public void setEncodePo4LcNo(String encodePo4LcNo) {
        this.encodePo4LcNo = encodePo4LcNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
