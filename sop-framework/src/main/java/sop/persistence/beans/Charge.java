package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:43
 * @Package: sop.persistence.beans
 */

public class Charge extends BaseBean {
    /**
     * table charge
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String chgCode;
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
}
