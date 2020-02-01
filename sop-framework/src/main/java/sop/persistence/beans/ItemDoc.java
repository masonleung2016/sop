package sop.persistence.beans;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:49
 * @Package: sop.persistence.beans
 */

public class ItemDoc extends BaseBean {

    private String docNo;

    private String itemNo;

    private String ofNo;

    private String poNo;

    private String soNo;

    private String qcNo;

    private String category;

    private String shortName;

    private String docType;

    private String path;

    @Override
    public Serializable getId() {
        return this.docNo;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getOfNo() {
        return ofNo;
    }

    public void setOfNo(String ofNo) {
        this.ofNo = ofNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getQcNo() {
        return qcNo;
    }

    public void setQcNo(String qcNo) {
        this.qcNo = qcNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String description) {
        this.path = description;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
