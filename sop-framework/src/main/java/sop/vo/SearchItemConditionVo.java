package sop.vo;

import dwz.persistence.BaseConditionVO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:57
 * @Package: sop.vo
 */


public class SearchItemConditionVo extends BaseConditionVO {
    /**
     * search list condition
     */
    private String itemNoFrom1;
    private String itemNoFrom2;
    private String itemNoFrom3;
    private String itemNoFrom;
    private String itemNoTo1;
    private String itemNoTo2;
    private String itemNoTo3;
    private String itemNoTo;
    private boolean indexFlag = true;
    private Integer fkItemMaster;
    private String itemMasterType;
    private Integer fkTemplate;
    private String templateName;

    private Integer fkTempType;

    public String getItemNoFrom1() {
        return itemNoFrom1;
    }

    public void setItemNoFrom1(String itemNoFrom1) {
        this.itemNoFrom1 = itemNoFrom1;
    }

    public String getItemNoFrom2() {
        return itemNoFrom2;
    }

    public void setItemNoFrom2(String itemNoFrom2) {
        this.itemNoFrom2 = itemNoFrom2;
    }

    public String getItemNoFrom3() {
        return itemNoFrom3;
    }

    public void setItemNoFrom3(String itemNoFrom3) {
        this.itemNoFrom3 = itemNoFrom3;
    }

    public String getItemNoFrom() {
        return itemNoFrom;
    }

    public void setItemNoFrom(String itemNoFrom) {
        this.itemNoFrom = itemNoFrom.trim();
    }

    public String getItemNoTo1() {
        return itemNoTo1;
    }

    public void setItemNoTo1(String itemNoTo1) {
        this.itemNoTo1 = itemNoTo1;
    }

    public String getItemNoTo2() {
        return itemNoTo2;
    }

    public void setItemNoTo2(String itemNoTo2) {
        this.itemNoTo2 = itemNoTo2;
    }

    public String getItemNoTo3() {
        return itemNoTo3;
    }

    public void setItemNoTo3(String itemNoTo3) {
        this.itemNoTo3 = itemNoTo3;
    }

    public String getItemNoTo() {
        return itemNoTo;
    }

    public void setItemNoTo(String itemNoTo) {
        this.itemNoTo = itemNoTo.trim();
    }

    public boolean isIndexFlag() {
        return indexFlag;
    }

    public void setIndexFlag(boolean indexFlag) {
        this.indexFlag = indexFlag;
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

    public Integer getFkTempType() {
        return fkTempType;
    }

    public void setFkTempType(Integer fkTempType) {
        this.fkTempType = fkTempType;
    }


}
