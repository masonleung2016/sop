package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:54
 * @Package: sop.persistence.beans
 */

public class PackingDetail extends BaseBean {
    
    private String pdNo;
    
    private String category;
    
    private String template;
    
    private String pdDetail;

    public String getPdNo() {
        return pdNo;
    }

    public void setPdNo(String pdNo) {
        this.pdNo = pdNo;
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

    public String getPdDetail() {
        return pdDetail;
    }

    public void setPdDetail(String pdDetail) {
        this.pdDetail = pdDetail;
    }

    @Override
    public Serializable getId() {
        return this.pdNo;
    }
}
