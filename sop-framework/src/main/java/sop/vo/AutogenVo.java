package sop.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:33
 * @Package: sop.vo
 */


public class AutogenVo {
    private Integer id;
    private String coCode;
    private String autTxType;
    private BigDecimal autTxNum;
    private String autTxPrefix;
    private String autDesc;

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

    public BigDecimal getAutTxNum() {
        return autTxNum;
    }

    public void setAutTxNum(BigDecimal autTxNum) {
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
}
