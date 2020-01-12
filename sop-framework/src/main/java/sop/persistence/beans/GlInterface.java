package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:48
 * @Package: sop.persistence.beans
 */

public class GlInterface extends BaseBean {
    /**
     * table glinf
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * co_code
     */
    private String coCode;
    /**
     * gl_inf_code
     */
    private String glInfCode;
    /**
     * gl_inf_desc
     */
    private String glInfDesc;
    /**
     * gl_inf_ac_dr
     */
    private String glInfAcDr;
    /**
     * gl_inf_dp_dr
     */
    private String glInfDpDr;
    /**
     * gl_inf_cst_dr
     */
    private String glInfCstDr;
    /**
     * gl_inf_ac_cr
     */
    private String glInfAcCr;
    /**
     * gl_inf_dp_cr
     */
    private String glInfDpCr;
    /**
     * gl_inf_cst_cr
     */
    private String glInfCstCr;
    /**
     * rev_no
     */
    private BigDecimal revNo;

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

    public String getGlInfCode() {
        return glInfCode;
    }

    public void setGlInfCode(String glInfCode) {
        this.glInfCode = glInfCode;
    }

    public String getGlInfDesc() {
        return glInfDesc;
    }

    public void setGlInfDesc(String glInfDesc) {
        this.glInfDesc = glInfDesc;
    }

    public String getGlInfAcDr() {
        return glInfAcDr;
    }

    public void setGlInfAcDr(String glInfAcDr) {
        this.glInfAcDr = glInfAcDr;
    }

    public String getGlInfDpDr() {
        return glInfDpDr;
    }

    public void setGlInfDpDr(String glInfDpDr) {
        this.glInfDpDr = glInfDpDr;
    }

    public String getGlInfCstDr() {
        return glInfCstDr;
    }

    public void setGlInfCstDr(String glInfCstDr) {
        this.glInfCstDr = glInfCstDr;
    }

    public String getGlInfAcCr() {
        return glInfAcCr;
    }

    public void setGlInfAcCr(String glInfAcCr) {
        this.glInfAcCr = glInfAcCr;
    }

    public String getGlInfDpCr() {
        return glInfDpCr;
    }

    public void setGlInfDpCr(String glInfDpCr) {
        this.glInfDpCr = glInfDpCr;
    }

    public String getGlInfCstCr() {
        return glInfCstCr;
    }

    public void setGlInfCstCr(String glInfCstCr) {
        this.glInfCstCr = glInfCstCr;
    }

    public BigDecimal getRevNo() {
        return revNo;
    }

    public void setRevNo(BigDecimal revNo) {
        this.revNo = revNo;
    }
}
