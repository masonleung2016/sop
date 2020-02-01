package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:49
 * @Package: sop.persistence.beans
 */

public class ImproveSuggestion extends BaseBean {

    private String impNo;

    private String category;

    private String template;

    private String impDetail;

    public String getImpNo() {
        return impNo;
    }

    public void setImpNo(String pbNo) {
        this.impNo = pbNo;
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

    public String getImpDetail() {
        return impDetail;
    }

    public void setImpDetail(String pbDetail) {
        this.impDetail = pbDetail;
    }

    @Override
    public Serializable getId() {
        return this.impNo;
    }
}
