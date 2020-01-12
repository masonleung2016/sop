package sop.vo;

import java.math.BigDecimal;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:34
 * @Package: sop.vo
 */


public class ChargeVo {
    private Integer id;
    private String chgCode;
    private String encodeChgCode;
    private String chgDesc;
    private BigDecimal chgRate;
    private BigDecimal chgAmt;
    private String chgGlInf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChgCode() {
        return chgCode;
    }

    public void setChgCode(String chgCode) {
        this.chgCode = chgCode;
        this.encodeChgCode = Base64.encode(chgCode, "utf-8");
    }

    public String getChgDesc() {
        return chgDesc;
    }

    public void setChgDesc(String chgDesc) {
        this.chgDesc = chgDesc;
    }

    public BigDecimal getChgRate() {
        return chgRate;
    }

    public void setChgRate(BigDecimal chgRate) {
        this.chgRate = chgRate;
    }

    public BigDecimal getChgAmt() {
        return chgAmt;
    }

    public void setChgAmt(BigDecimal chgAmt) {
        this.chgAmt = chgAmt;
    }

    public String getChgGlInf() {
        return chgGlInf;
    }

    public void setChgGlInf(String chgGlInf) {
        this.chgGlInf = chgGlInf;
    }

    public String getEncodeChgCode() {
        return encodeChgCode;
    }

    public void setEncodeChgCode(String encodeChgCode) {
        this.encodeChgCode = encodeChgCode;
    }
}
