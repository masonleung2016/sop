package sop.vo;

import sop.persistence.beans.QcItemBase;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:55
 * @Package: sop.vo
 */


public class QualityCheckItemVo extends QcItemBase {

    private static final long serialVersionUID = -1048689537594984065L;
    /**
     * id
     */
    private Integer id;
    /**
     * qc2_no
     */
    private String qc2No;
    /**
     * qc2_pic
     */
    private String qc2Pic;
    /**
     * qc2_part_pic
     */
    private String qc2PartPic;
    /**
     * qc2_it_cat
     */
    private String qc2ItCat;
    /**
     * qc2_it_no
     */
    private String qc2ItNo;
    /**
     * qc2_it_suffix
     */
    private String qc2ItSuffix;
    /**
     * qc2_it_cat_no_suffix
     */
    private String qc2ItCatNoSuffix;
    /**
     * qc2_it_name
     */
    private String qc2ItName;
    private String qc2Cl;

    public QualityCheckItemVo() {
    }

    public QualityCheckItemVo(String qcNo, String itCatNoSuffix) {
        this.qc2No = qcNo;
        this.qc2ItCatNoSuffix = itCatNoSuffix;
    }

    public String getQc2Cl() {
        return qc2Cl;
    }

    public void setQc2Cl(String qc2cl) {
        this.qc2Cl = qc2cl;
    }

    public String getQc2No() {
        return qc2No;
    }

    public void setQc2No(String qc2No) {
        this.qc2No = qc2No;
    }

    public String getQc2Pic() {
        return qc2Pic;
    }

    public void setQc2Pic(String qc2Pic) {
        this.qc2Pic = qc2Pic;
    }

    public String getQc2PartPic() {
        return qc2PartPic;
    }

    public void setQc2PartPic(String qc2PartPic) {
        this.qc2PartPic = qc2PartPic;
    }

    public String getQc2ItCat() {
        return qc2ItCat;
    }

    public void setQc2ItCat(String qc2ItCat) {
        this.qc2ItCat = qc2ItCat;
    }

    public String getQc2ItNo() {
        return qc2ItNo;
    }

    public void setQc2ItNo(String qc2ItNo) {
        this.qc2ItNo = qc2ItNo;
    }

    public String getQc2ItSuffix() {
        return qc2ItSuffix;
    }

    public void setQc2ItSuffix(String qc2ItSuffix) {
        this.qc2ItSuffix = qc2ItSuffix;
    }

    public String getQc2ItCatNoSuffix() {
        return qc2ItCatNoSuffix;
    }

    public void setQc2ItCatNoSuffix(String qc2ItCatNoSuffix) {
        this.qc2ItCatNoSuffix = qc2ItCatNoSuffix;
    }

    public String getQc2ItName() {
        return qc2ItName;
    }

    public void setQc2ItName(String qc2ItName) {
        this.qc2ItName = qc2ItName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
