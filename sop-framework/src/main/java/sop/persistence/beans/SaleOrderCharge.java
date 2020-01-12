package sop.persistence.beans;

import java.math.BigDecimal;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:06
 * @Package: sop.persistence.beans
 */


public class SaleOrderCharge extends BaseBean {

    /**
     * sotx5
     */
    private static final long serialVersionUID = -185248557316796065L;
    private Integer id;
    /**
     * co_code
     */
    private String coCode;
    /**
     * so5_ccy
     */
    private String so5Ccy;
    /**
     * so5_chg_amt
     */
    private BigDecimal so5ChgAmt;
    /**
     * so5_chg_code
     */
    private String so5ChgCode;
    private String encodeSo5ChgCode;
    /**
     * so5_chg_desc
     */
    private String so5ChgDesc;
    /**
     * so5_chg_rate
     */
    private BigDecimal so5ChgRate;
    /**
     * so5_ex_rate
     */
    private BigDecimal so5ExRate;
    /**
     * so5_no
     */
    private String so5No;
    /**
     * so5_remk
     */
    private String so5Remk;
    public SaleOrderCharge() {

    }
    public SaleOrderCharge(Charge charge) {
        this.so5ChgCode = charge.getChgCode();
        this.so5ChgDesc = charge.getChgDesc();
        this.so5ChgRate = charge.getChgRate();
        this.so5ChgAmt = charge.getChgAmt();
        this.encodeSo5ChgCode = Base64.encode(so5ChgCode, "utf-8");
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getSo5Ccy() {
        return so5Ccy;
    }

    public void setSo5Ccy(String so5Ccy) {
        this.so5Ccy = so5Ccy;
    }

    public BigDecimal getSo5ChgAmt() {
        return so5ChgAmt;
    }

    public void setSo5ChgAmt(BigDecimal so5ChgAmt) {
        this.so5ChgAmt = so5ChgAmt;
    }

    public String getSo5ChgCode() {
        return so5ChgCode;
    }

    public void setSo5ChgCode(String so5ChgCode) {
        this.so5ChgCode = so5ChgCode;
        this.encodeSo5ChgCode = Base64.encode(so5ChgCode, "utf-8");
    }

    public String getEncodeSo5ChgCode() {
        return encodeSo5ChgCode;
    }

    public void setEncodeSo5ChgCode(String encodeSo5ChgCode) {
        this.encodeSo5ChgCode = encodeSo5ChgCode;
    }

    public String getSo5ChgDesc() {
        return so5ChgDesc;
    }

    public void setSo5ChgDesc(String so5ChgDesc) {
        this.so5ChgDesc = so5ChgDesc;
    }

    public BigDecimal getSo5ChgRate() {
        return so5ChgRate;
    }

    public void setSo5ChgRate(BigDecimal so5ChgRate) {
        this.so5ChgRate = so5ChgRate;
    }

    public BigDecimal getSo5ExRate() {
        return so5ExRate;
    }

    public void setSo5ExRate(BigDecimal so5ExRate) {
        this.so5ExRate = so5ExRate;
    }

    public String getSo5No() {
        return so5No;
    }

    public void setSo5No(String so5No) {
        this.so5No = so5No;
    }

    public String getSo5Remk() {
        return so5Remk;
    }

    public void setSo5Remk(String so5Remk) {
        this.so5Remk = so5Remk;
    }
}
