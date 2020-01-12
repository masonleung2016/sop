package sop.vo;

import java.math.BigDecimal;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:45
 * @Package: sop.vo
 */


public class PayTermVo {
    /**
     * pay_code
     */
    private String payCode;

    private String encodePayCode;
    /**
     * pay_desc
     */
    private String payDesc;
    /**
     * pay_days
     */
    private BigDecimal payDays;
    /**
     * pay_date
     */
    private Double payDate;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
        this.encodePayCode = Base64.encode(payCode, "utf-8");
    }

    public String getEncodePayCode() {
        return encodePayCode;
    }

    public void setEncodePayCode(String encodePayCode) {
        this.encodePayCode = encodePayCode;
    }

    public String getPayDesc() {
        return payDesc;
    }

    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    public BigDecimal getPayDays() {
        return payDays;
    }

    public void setPayDays(BigDecimal payDays) {
        this.payDays = payDays;
    }

    public Double getPayDate() {
        return payDate;
    }

    public void setPayDate(Double payDate) {
        this.payDate = payDate;
    }
}
