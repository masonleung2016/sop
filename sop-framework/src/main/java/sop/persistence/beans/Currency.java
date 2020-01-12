package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:45
 * @Package: sop.persistence.beans
 */

public class Currency extends BaseBean {
    /**
     * table curr
     */
    private static final long serialVersionUID = 1L;

    //field name和字段名一一对应，除了id
    private Integer id;
    private String ccyCode;
    private String ccyDesc;
    private String ccyChn;
    private BigDecimal ccyRate;
    private String ccyRef;// no use?

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public String getCcyDesc() {
        return ccyDesc;
    }

    public void setCcyDesc(String ccyDesc) {
        this.ccyDesc = ccyDesc;
    }

    public String getCcyChn() {
        return ccyChn;
    }

    public void setCcyChn(String ccyChn) {
        this.ccyChn = ccyChn;
    }

    public BigDecimal getCcyRate() {
        return ccyRate;
    }

    public void setCcyRate(BigDecimal ccyRate) {
        this.ccyRate = ccyRate;
    }


    public String getCcyRef() {
        return ccyRef;
    }

    public void setCcyRef(String ccyRef) {
        this.ccyRef = ccyRef;
    }
}
