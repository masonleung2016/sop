package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:07
 * @Package: sop.persistence.beans
 */


public class SaleOrderLc extends BaseBean {

    /**
     * sotx4lc
     */
    private static final long serialVersionUID = -185248557316796065L;
    private Integer id;
    /**
     * co_code
     */
    private String coCode;
    /**
     * so4_no
     */
    private String so4No;
    /**
     * so4_lc_no
     */
    private String so4LcNo;
    private String encodeSo4LcNo;
    /**
     * so4_exp_date
     */
    private Date so4ExpDate;
    /**
     * so4_lc_amt
     */
    private BigDecimal so4LcAmt;
    /**
     * so4_settle_date
     */
    private Date so4SettleDate;
    /**
     * so4_settle_amt
     */
    private BigDecimal so4SettleAmt;
    public SaleOrderLc() {

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

    public String getSo4No() {
        return so4No;
    }

    public void setSo4No(String so4No) {
        this.so4No = so4No;
    }

    public String getSo4LcNo() {
        return so4LcNo;
    }

    public void setSo4LcNo(String so4LcNo) {
        this.so4LcNo = so4LcNo;
        this.encodeSo4LcNo = Base64.encode(so4LcNo, "utf-8");
    }

    public String getEncodeSo4LcNo() {
        return encodeSo4LcNo;
    }

    public void setEncodeSo4LcNo(String encodeSo4LcNo) {
        this.encodeSo4LcNo = encodeSo4LcNo;
    }

    public Date getSo4ExpDate() {
        return so4ExpDate;
    }

    public void setSo4ExpDate(Date so4ExpDate) {
        this.so4ExpDate = so4ExpDate;
    }

    public BigDecimal getSo4LcAmt() {
        return so4LcAmt;
    }

    public void setSo4LcAmt(BigDecimal so4LcAmt) {
        this.so4LcAmt = so4LcAmt;
    }

    public Date getSo4SettleDate() {
        return so4SettleDate;
    }

    public void setSo4SettleDate(Date so4SettleDate) {
        this.so4SettleDate = so4SettleDate;
    }

    public BigDecimal getSo4SettleAmt() {
        return so4SettleAmt;
    }

    public void setSo4SettleAmt(BigDecimal so4SettleAmt) {
        this.so4SettleAmt = so4SettleAmt;
    }
}
