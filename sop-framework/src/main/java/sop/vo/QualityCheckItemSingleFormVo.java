package sop.vo;

import java.io.Serializable;

import dwz.dal.object.AbstractDO;
import sop.persistence.beans.BaseBean;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:54
 * @Package: sop.vo
 */

public class QualityCheckItemSingleFormVo extends AbstractDO {


    private String qcNo;
    private String itCatNoSuffix;
    private String form;
    private String data;

    public QualityCheckItemSingleFormVo() {
    }

    public String getQcNo() {
        return qcNo;
    }

    public void setQcNo(String qcNo) {
        this.qcNo = qcNo;
    }

    public String getItCatNoSuffix() {
        return itCatNoSuffix;
    }

    public void setItCatNoSuffix(String itCatNoSuffix) {
        this.itCatNoSuffix = itCatNoSuffix;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public Serializable getId() {
        // TODO Auto-generated method stub
        return null;
    }


}
