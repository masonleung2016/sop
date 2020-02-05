package sop.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:58
 * @Package: sop.persistence.beans
 */

public class ProductItem extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String itemMasterType;

    private String templateName;

    private String productNo;

    private String productEngName;

    private String productChnName;

    private Integer fkTemplate;

    private String details;

    private Date importdatetime;

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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductEngName() {
        return productEngName;
    }

    public void setProductEngName(String productEngName) {
        this.productEngName = productEngName;
    }

    public String getProductChnName() {
        return productChnName;
    }

    public void setProductChnName(String productChnName) {
        this.productChnName = productChnName;
    }

    public Integer getFkTemplate() {
        return fkTemplate;
    }

    public void setFkTemplate(Integer fkTemplate) {
        this.fkTemplate = fkTemplate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getImportdatetime() {
        return importdatetime;
    }

    public void setImportdatetime(Date importdatetime) {
        this.importdatetime = importdatetime;
    }
}
