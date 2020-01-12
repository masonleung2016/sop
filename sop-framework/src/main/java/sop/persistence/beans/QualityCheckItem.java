package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:05
 * @Package: sop.persistence.beans
 */


public class QualityCheckItem extends QcItemBase {

    private static final long serialVersionUID = 1L;

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

    /**
     * qc2_it_details
     */
    private String qc2ItDetails;

    /**
     * 验货通知单 qc2_have_ins_not
     */
    private boolean qc2HaveInsNot;

    /**
     * 验货通知单 qc2_ins_not
     */
    private String qc2InsNot;

    /**
     * qc2_bzmx
     */
    private String qc2Bzmx;

    /**
     * qc2_bz
     */
    private String qc2Bz;

    /**
     * qc2_bzwlqd
     */
    private String qc2Bzwlqd;

    /**
     * qc2_dhgj
     */
    private String qc2Dhgj;

    /**
     * qc2_cp
     */
    private String qc2Cp;

    /**
     * qc2_ins_rep
     */
    private String qc2InsRep;

    /**
     * qc2_have_zgyq
     */
    //private boolean qc2HaveZgyq;

    /**
     * qc2_zgyq
     */
    private String qc2Zgyq;

    /**
     * qc2_have_sfcsbg
     */
    //private boolean qc2HaveSfcsbg;

    /**
     * qc2_sfcsbg
     */
    private String qc2Sfcsbg;

    /**
     * qc2_have_ysjc
     */
    //private boolean qc2HaveYsjc;

    /**
     * qc2_ysjc
     */
    private String qc2Ysjc;

    /**
     * qc2_have_syk
     */
    //private boolean qc2HaveSyk;

    /**
     * qc2_syk
     */
    private String qc2Syk;

    /**
     * qc2_have_fyhsm
     */
    //private boolean qc2HaveFyhsm;

    /**
     * qc2_fyhsm
     */
    private String qc2Fyhsm;

    /**
     * qc2_have_sy
     */
    //private boolean qc2HaveSy;

    /**
     * qc2_sy
     */
    private String qc2Sy;

    /**
     * qc2_have_tpcc
     */
    //private boolean qc2HaveTpcc;

    /**
     * qc2_tpcc
     */
    private String qc2Tpcc;

    /**
     * qc2_have_wty
     */
    //private boolean qc2HaveWty;

    /**
     * qc2_wty
     */
    private String qc2Wty;

    /**
     * qc2_have_kx
     */
    //private boolean qc2HaveKx;

    /**
     * qc2_kx
     */
    private String qc2Kx;

    /**
     * qc2_have_cc
     */
    //private boolean qc2HaveCc;

    /**
     * qc2_cc
     */
    private String qc2Cc;

    /**
     * qc2_have_lljybg
     */
    //private boolean qc2HaveLljybg;

    /**
     * qc2_lljybg
     */
    private String qc2Lljybg;

    /**
     * qc2_have_zcjyrbg
     */
    //private boolean qc2HaveZcjyrbg;

    /**
     * qc2_zcjyrbg
     */
    private String qc2Zcjyrbg;

    /**
     * qc2_have_scjhkz
     */
    //private boolean qc2HaveScjhkz;

    /**
     * qc2_scjhkz
     */
    private String qc2Scjhkz;

    /**
     * qc2_have_gzcsxq
     */
    //private boolean qc2HaveGzcsxq;

    /**
     * qc2_gzcsxq
     */
    private String qc2Gzcsxq;

    /**
     * qc2_have_jg
     */
    //private boolean qc2HaveJg;

    /**
     * qc2_jg
     */
    private String qc2Jg;

    /**
     * qc2_have_zn
     */
    //private boolean qc2HaveZn;

    /**
     * qc2_zn
     */
    private String qc2Zn;

    /**
     * qc2_have_gfx
     */
    //private boolean qc2HaveGfx;

    /**
     * qc2_gfx
     */
    private String qc2Gfx;

    /**
     * qc2_have_clsj
     */
    //private boolean qc2HaveClsj;

    /**
     * qc2_clsj
     */
    private String qc2Clsj;

    /**
     * qc2_have_ycqktzcld
     */
    //private boolean qc2HaveYcqktzcld;

    /**
     * qc2_ycqktzcld
     */
    private String qc2Ycqktzcld;

    /**
     * qc2_have_wpcgz
     */
    //private boolean qc2HaveWpcgz;

    /**
     * qc2_wpcgz
     */
    private String qc2Wpcgz;

    /**
     * qc2_have_csyq
     */
    //private boolean qc2HaveCsyq;

    /**
     * qc2_csyq
     */
    private String qc2Csyq;

    /**
     * qc2_have_yhzj
     */
    //private boolean qc2HaveYhzj;

    /**
     * qc2_yhzj
     */
    private String qc2Yhzj;


    /**
     * qc2_checklist
     */
    private String qc2Cl;
    private Integer id;

    public QualityCheckItem() {
    }

    public QualityCheckItem(PurchaseOrderItem purchaseOrderItem) {
        this.qc2No = purchaseOrderItem.getPo2No();
        this.qc2ItCat = purchaseOrderItem.getPo2ItCat();
        this.qc2ItNo = purchaseOrderItem.getPo2ItNo();
        this.qc2ItSuffix = purchaseOrderItem.getPo2ItSuffix();
        this.qc2ItCatNoSuffix = purchaseOrderItem.getPo2ItCatNoSuffix();
        this.qc2ItName = purchaseOrderItem.getPo2ItName();
        this.qc2ItDetails = purchaseOrderItem.getPo2ItDetails();
        setCrtUsr(purchaseOrderItem.getCrtUsr());
        setCrtDate(purchaseOrderItem.getCrtDate());
        setModUsr(purchaseOrderItem.getModUsr());
        setModDate(purchaseOrderItem.getModDate());
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

    public String getQc2ItName() {
        return qc2ItName;
    }

    public void setQc2ItName(String qc2ItName) {
        this.qc2ItName = qc2ItName;
    }

    public String getQc2ItDetails() {
        return qc2ItDetails;
    }

    public void setQc2ItDetails(String qc2ItDetails) {
        this.qc2ItDetails = qc2ItDetails;
    }

    public boolean isQc2HaveInsNot() {
        return qc2HaveInsNot;
    }

    public void setQc2HaveInsNot(boolean qc2HaveInsNot) {
        this.qc2HaveInsNot = qc2HaveInsNot;
    }

    public String getQc2InsNot() {
        return qc2InsNot;
    }

    public void setQc2InsNot(String qc2InsNot) {
        this.qc2InsNot = qc2InsNot;
    }

    public String getQc2ItCatNoSuffix() {
        return qc2ItCatNoSuffix;
    }

    public void setQc2ItCatNoSuffix(String qc2ItCatNoSuffix) {
        this.qc2ItCatNoSuffix = qc2ItCatNoSuffix;
    }

    public String getQc2Bzmx() {
        return qc2Bzmx;
    }

    public void setQc2Bzmx(String qc2Bzmx) {
        this.qc2Bzmx = qc2Bzmx;
    }

    public String getQc2Bz() {
        return qc2Bz;
    }

    public void setQc2Bz(String qc2Bz) {
        this.qc2Bz = qc2Bz;
    }

    public String getQc2Bzwlqd() {
        return qc2Bzwlqd;
    }

    public void setQc2Bzwlqd(String qc2Bzwlqd) {
        this.qc2Bzwlqd = qc2Bzwlqd;
    }

    public String getQc2Dhgj() {
        return qc2Dhgj;
    }

    public void setQc2Dhgj(String qc2Dhgj) {
        this.qc2Dhgj = qc2Dhgj;
    }

    public String getQc2InsRep() {
        return qc2InsRep;
    }

    public void setQc2InsRep(String qc2InsRep) {
        this.qc2InsRep = qc2InsRep;
    }

    public String getQc2Zgyq() {
        return qc2Zgyq;
    }

    public void setQc2Zgyq(String qc2Zgyq) {
        this.qc2Zgyq = qc2Zgyq;
    }

    public String getQc2Sfcsbg() {
        return qc2Sfcsbg;
    }

    public void setQc2Sfcsbg(String qc2Sfcsbg) {
        this.qc2Sfcsbg = qc2Sfcsbg;
    }

    public String getQc2Ysjc() {
        return qc2Ysjc;
    }

    public void setQc2Ysjc(String qc2Ysjc) {
        this.qc2Ysjc = qc2Ysjc;
    }

    public String getQc2Syk() {
        return qc2Syk;
    }

    public void setQc2Syk(String qc2Syk) {
        this.qc2Syk = qc2Syk;
    }

    public String getQc2Fyhsm() {
        return qc2Fyhsm;
    }

    public void setQc2Fyhsm(String qc2Fyhsm) {
        this.qc2Fyhsm = qc2Fyhsm;
    }

    public String getQc2Sy() {
        return qc2Sy;
    }

    public void setQc2Sy(String qc2Sy) {
        this.qc2Sy = qc2Sy;
    }

    public String getQc2Tpcc() {
        return qc2Tpcc;
    }

    public void setQc2Tpcc(String qc2Tpcc) {
        this.qc2Tpcc = qc2Tpcc;
    }

    public String getQc2Wty() {
        return qc2Wty;
    }

    public void setQc2Wty(String qc2Wty) {
        this.qc2Wty = qc2Wty;
    }

    public String getQc2Kx() {
        return qc2Kx;
    }

    public void setQc2Kx(String qc2Kx) {
        this.qc2Kx = qc2Kx;
    }

    public String getQc2Cc() {
        return qc2Cc;
    }

    public void setQc2Cc(String qc2Cc) {
        this.qc2Cc = qc2Cc;
    }

    public String getQc2Lljybg() {
        return qc2Lljybg;
    }

    public void setQc2Lljybg(String qc2Lljybg) {
        this.qc2Lljybg = qc2Lljybg;
    }

    public String getQc2Zcjyrbg() {
        return qc2Zcjyrbg;
    }

    public void setQc2Zcjyrbg(String qc2Zcjyrbg) {
        this.qc2Zcjyrbg = qc2Zcjyrbg;
    }

    public String getQc2Scjhkz() {
        return qc2Scjhkz;
    }

    public void setQc2Scjhkz(String qc2Scjhkz) {
        this.qc2Scjhkz = qc2Scjhkz;
    }

    public String getQc2Gzcsxq() {
        return qc2Gzcsxq;
    }

    public void setQc2Gzcsxq(String qc2Gzcsxq) {
        this.qc2Gzcsxq = qc2Gzcsxq;
    }

    public String getQc2Jg() {
        return qc2Jg;
    }

    public void setQc2Jg(String qc2Jg) {
        this.qc2Jg = qc2Jg;
    }

    public String getQc2Zn() {
        return qc2Zn;
    }

    public void setQc2Zn(String qc2Zn) {
        this.qc2Zn = qc2Zn;
    }

    public String getQc2Gfx() {
        return qc2Gfx;
    }

    public void setQc2Gfx(String qc2Gfx) {
        this.qc2Gfx = qc2Gfx;
    }

    public String getQc2Clsj() {
        return qc2Clsj;
    }

    public void setQc2Clsj(String qc2Clsj) {
        this.qc2Clsj = qc2Clsj;
    }

    public String getQc2Ycqktzcld() {
        return qc2Ycqktzcld;
    }

    public void setQc2Ycqktzcld(String qc2Ycqktzcld) {
        this.qc2Ycqktzcld = qc2Ycqktzcld;
    }

    public String getQc2Wpcgz() {
        return qc2Wpcgz;
    }

    public void setQc2Wpcgz(String qc2Wpcgz) {
        this.qc2Wpcgz = qc2Wpcgz;
    }

    public String getQc2Csyq() {
        return qc2Csyq;
    }

    public void setQc2Csyq(String qc2Csyq) {
        this.qc2Csyq = qc2Csyq;
    }

    public String getQc2Yhzj() {
        return qc2Yhzj;
    }

    public void setQc2Yhzj(String qc2Yhzj) {
        this.qc2Yhzj = qc2Yhzj;
    }

    public String getQc2Cp() {
        return qc2Cp;
    }

    public void setQc2Cp(String qc2Cp) {
        this.qc2Cp = qc2Cp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQc2Cl() {
        return qc2Cl;
    }

    public void setQc2Cl(String qc2cl) {
        this.qc2Cl = qc2cl;
    }


}
