package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:37
 * @Package: sop.vo
 */

public class SearchTemplateConditionVo {
    
    private Integer fkItemMaster;
    
    private String itemMasterType;
    
    private Integer fkTemplate;
    
    private String templateName;
    
    private String engTemplateName;

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

    public Integer getFkItemMaster() {
        return fkItemMaster;
    }

    public void setFkItemMaster(Integer fkItemMaster) {
        this.fkItemMaster = fkItemMaster;
    }

    public Integer getFkTemplate() {
        return fkTemplate;
    }

    public void setFkTemplate(Integer fkTemplate) {
        this.fkTemplate = fkTemplate;
    }

    public String getEngTemplateName() {
        return engTemplateName;
    }

    public void setEngTemplateName(String engTemplateName) {
        this.engTemplateName = engTemplateName;
    }
}
