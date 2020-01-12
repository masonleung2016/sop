package sop.persistence.beans;

import java.math.BigDecimal;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:59
 * @Package: sop.persistence.beans
 */

public class PurchaseOrderCharge extends BaseBean {

    /**
     * potx5
     */
    private static final long serialVersionUID = -185248557316796065L;
    /**
     * po5_ccy
     */
    private String po5Ccy;
    /**
     * po5_chg_amt
     */
    private BigDecimal po5ChgAmt;
    /**
     * po5_chg_code
     */
    private String po5ChgCode;
    private String encodePo5ChgCode;
    /**
     * po5_chg_desc
     */
    private String po5ChgDesc;
    /**
     * po5_chg_rate
     */
    private BigDecimal po5ChgRate;
    /**
     * po5_ex_rate
     */
    private BigDecimal po5ExRate;
    /**
     * po5_no
     */
    private String po5No;
    /**
     * po5_remk
     */
    private String po5Remk;
    private Integer id;
    public PurchaseOrderCharge() {

    }
    public PurchaseOrderCharge(Charge charge) {
        this.po5ChgCode = charge.getChgCode();
        this.po5ChgDesc = charge.getChgDesc();
        this.po5ChgRate = charge.getChgRate();
        this.po5ChgAmt = charge.getChgAmt();
        this.encodePo5ChgCode = Base64.encode(po5ChgCode, "utf-8");
    }

    public PurchaseOrderCharge(SaleOrderCharge saleOrderCharge) {
        this.po5Remk = saleOrderCharge.getSo5Remk();
        this.po5Ccy = saleOrderCharge.getSo5Ccy();
        this.po5ChgAmt = saleOrderCharge.getSo5ChgAmt();
        this.po5ChgCode = saleOrderCharge.getSo5ChgCode();
        this.po5ChgDesc = saleOrderCharge.getSo5ChgDesc();
        this.po5ChgRate = saleOrderCharge.getSo5ChgRate();
        this.po5ExRate = saleOrderCharge.getSo5ExRate();
        this.encodePo5ChgCode = Base64.encode(po5ChgCode, "utf-8");
    }

    public String getPo5Ccy() {
        return po5Ccy;
    }

    public void setPo5Ccy(String po5Ccy) {
        this.po5Ccy = po5Ccy;
    }

    public BigDecimal getPo5ChgAmt() {
        return po5ChgAmt;
    }

    public void setPo5ChgAmt(BigDecimal po5ChgAmt) {
        this.po5ChgAmt = po5ChgAmt;
    }

    public String getPo5ChgCode() {
        return po5ChgCode;
    }

    public void setPo5ChgCode(String po5ChgCode) {
        this.po5ChgCode = po5ChgCode;
        this.encodePo5ChgCode = Base64.encode(po5ChgCode, "utf-8");
    }

    public String getPo5ChgDesc() {
        return po5ChgDesc;
    }

    public void setPo5ChgDesc(String po5ChgDesc) {
        this.po5ChgDesc = po5ChgDesc;
    }

    public BigDecimal getPo5ChgRate() {
        return po5ChgRate;
    }

    public void setPo5ChgRate(BigDecimal po5ChgRate) {
        this.po5ChgRate = po5ChgRate;
    }

    public BigDecimal getPo5ExRate() {
        return po5ExRate;
    }

    public void setPo5ExRate(BigDecimal po5ExRate) {
        this.po5ExRate = po5ExRate;
    }

    public String getPo5No() {
        return po5No;
    }

    public void setPo5No(String po5No) {
        this.po5No = po5No;
    }

    public String getPo5Remk() {
        return po5Remk;
    }

    public void setPo5Remk(String po5Remk) {
        this.po5Remk = po5Remk;
    }

    public String getEncodePo5ChgCode() {
        return encodePo5ChgCode;
    }

    public void setEncodePo5ChgCode(String encodePo5ChgCode) {
        this.encodePo5ChgCode = encodePo5ChgCode;
    }

    public Integer getId() {
        return id;
    }
}
