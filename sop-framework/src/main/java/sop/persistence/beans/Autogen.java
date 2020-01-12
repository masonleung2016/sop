package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:41
 * @Package: sop.persistence.beans
 */

public class Autogen extends BaseBean {
    /**
     * table autogen
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String coCode;
    private String autTxType;
    private String autTxNum;
    private String autTxPrefix;
    private String autDesc;
    private String autSysFunc;
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

    public String getAutTxType() {
        return autTxType;
    }

    public void setAutTxType(String autTxType) {
        this.autTxType = autTxType;
    }

    public String getAutTxNum() {
        return autTxNum;
    }

    public void setAutTxNum(String autTxNum) {
        this.autTxNum = autTxNum;
    }

    public String getAutTxPrefix() {
        return autTxPrefix;
    }

    public void setAutTxPrefix(String autTxPrefix) {
        this.autTxPrefix = autTxPrefix;
    }

    public String getAutDesc() {
        return autDesc;
    }

    public void setAutDesc(String autDesc) {
        this.autDesc = autDesc;
    }

    public String getAutSysFunc() {
        return autSysFunc;
    }

    public void setAutSysFunc(String autSysFunc) {
        this.autSysFunc = autSysFunc;
    }

    public BigDecimal getRevNo() {
        return revNo;
    }

    public void setRevNo(BigDecimal revNo) {
        this.revNo = revNo;
    }
}
