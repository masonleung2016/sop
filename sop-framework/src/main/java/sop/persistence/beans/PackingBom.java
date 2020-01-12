package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:53
 * @Package: sop.persistence.beans
 */

public class PackingBom extends BaseBean {
    private String pbNo;
    private String category;
    private String template;
    private String pbDetail;

    public String getPbNo() {
        return pbNo;
    }

    public void setPbNo(String pbNo) {
        this.pbNo = pbNo;
    }

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

    public String getPbDetail() {
        return pbDetail;
    }

    public void setPbDetail(String pbDetail) {
        this.pbDetail = pbDetail;
    }

    @Override
    public Serializable getId() {
        return this.pbNo;
    }

}
