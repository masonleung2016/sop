package sop.persistence.beans;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:43
 * @Package: sop.persistence.beans
 */

public class Checklist extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String itemMasterType;
    private String templateName;
    private Integer fkOrderProduct;
    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemMasterType() {
        return itemMasterType;
    }

    public void setItemMasterType(String itemMasterType) {
        this.itemMasterType = itemMasterType;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getFkOrderProduct() {
        return fkOrderProduct;
    }

    public void setFkOrderProduct(Integer fkOrderProduct) {
        this.fkOrderProduct = fkOrderProduct;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
