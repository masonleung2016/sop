package sop.persistence.beans;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:42
 * @Package: sop.persistence.beans
 */
public abstract class BaseBean extends AbstractDO {

    private static final long serialVersionUID = -5409310829157611021L;

    private String coCode;

    private String crtUsr;

    private Date crtDate;

    private String modUsr;

    private Date modDate;

    private Boolean deleted;

    public abstract Serializable getId();

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
