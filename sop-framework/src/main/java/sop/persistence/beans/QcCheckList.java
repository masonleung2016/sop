package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:02
 * @Package: sop.persistence.beans
 */

public class QcCheckList extends BaseBean {

    private String qctNo;

    private String category;

    private String template;

    private String qctDetail;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public Serializable getId() {
        return this.qctNo;
    }

    public String getQctNo() {
        return qctNo;
    }

    public void setQctNo(String qctNo) {
        this.qctNo = qctNo;
    }

    public String getQctDetail() {
        return qctDetail;
    }

    public void setQctDetail(String qctDetail) {
        this.qctDetail = qctDetail;
    }
}
