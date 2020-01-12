package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:10
 * @Package: sop.persistence.beans
 */


public class Uom extends AbstractDO {
    /**
     * table uom
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String uCode;
    private String uDesc;
    private String uChn;
    private BigDecimal uRate;
    private String crtUsr;
    private Date crtDate;
    private String modUsr;
    private Date modDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public String getuDesc() {
        return uDesc;
    }

    public void setuDesc(String uDesc) {
        this.uDesc = uDesc;
    }

    public String getuChn() {
        return uChn;
    }

    public void setuChn(String uChn) {
        this.uChn = uChn;
    }

    public BigDecimal getuRate() {
        return uRate;
    }

    public void setuRate(BigDecimal uRate) {
        this.uRate = uRate;
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getModUsr() {
        return modUsr;
    }

    public void setModUsr(String modUsr) {
        this.modUsr = modUsr;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
}
