package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:00
 * @Package: sop.persistence.beans
 */
public class PurchaseOrderItem extends BaseBean {

    /**
     * potx2
     */
    private static final long serialVersionUID = -185248557316796065L;
    /**
     * po2_amt
     */
    private BigDecimal po2Amt;
    /**
     * po2_appendix
     */
    private String po2Appendix;
    /**
     * po2_article
     */
    private String po2Article;
    /**
     * po2_ean
     */
    private String po2Ean;
    /**
     * po2_exp_ctn_pcs
     */
    private String po2ExpCtnPcs;
    /**
     * po2_exp_uom
     */
    private String po2ExpUom;
    /**
     * po2_inn_ctn_pcs
     */
    private String po2InnCtnPcs;
    /**
     * po2_inn_uom
     */
    private String po2InnUom;
    /**
     * po2_it_cat
     */
    private String po2ItCat;
    /**
     * po2_it_details
     */
    private String po2ItDetails;
    /**
     * po2_it_name
     */
    private String po2ItName;
    /**
     * po2_it_no
     */
    private String po2ItNo;
    /**
     * po2_it_suffix
     */
    private String po2ItSuffix;
    /**
     * po2_no
     */
    private String po2No;
    /**
     * po2_ord_qty
     */
    private BigDecimal po2OrdQty;
    /**
     * po2_remk
     */
    private String po2Remk;
    /**
     * po2_shd_qty
     */
    private BigDecimal po2ShdQty;
    /**
     * po2_so_no
     */
    private String po2SoNo;
    /**
     * po2_uom
     */
    private String po2Uom;
    /**
     * po2_uprice
     */
    private BigDecimal po2Uprice;
    private String po2ItCatNoSuffix;
    private Integer id;
    public PurchaseOrderItem() {

    }
    public PurchaseOrderItem(ItemMaster itemMaster) {
        this.po2SoNo = "0000000000";
        this.po2ItCat = itemMaster.getItCat();
        this.po2ItNo = itemMaster.getItNo();
        this.po2ItSuffix = itemMaster.getItSuffix();
        this.po2ItCatNoSuffix = this.po2ItCat + this.po2ItNo + this.po2ItSuffix;
        this.po2ItName = itemMaster.getItName();
        this.po2Uom = itemMaster.getItUom();
        this.po2Uprice = itemMaster.getItFtyFobCost();
        this.po2ItDetails = itemMaster.getDetailData();
    }

    public PurchaseOrderItem(OfferSheetItem offerSheetItem) {
        this.po2SoNo = "0000000000";
        this.po2ItCat = offerSheetItem.getOff2ItCat();
        this.po2ItNo = offerSheetItem.getOff2ItNo();
        this.po2ItSuffix = offerSheetItem.getOff2ItSuffix();
        this.po2ItName = offerSheetItem.getOff2ItName();
        this.po2ItDetails = offerSheetItem.getOff2DescOfferSh();
    }

    public PurchaseOrderItem(SaleOrderItem saleOrderItem) {
        this.po2Amt = saleOrderItem.getSo2Amt();
        this.po2Ean = saleOrderItem.getSo2Ean();
        this.po2ItCat = saleOrderItem.getSo2ItCat();
        this.po2ItDetails = saleOrderItem.getSo2ItDetails();
        this.po2ItName = saleOrderItem.getSo2ItName();
        this.po2ItNo = saleOrderItem.getSo2ItNo();
        this.po2ItSuffix = saleOrderItem.getSo2ItSuffix();
        this.po2SoNo = saleOrderItem.getSo2No();
        this.po2OrdQty = saleOrderItem.getSo2OrdQty();
        this.po2Remk = saleOrderItem.getSo2Remk();
        this.po2ShdQty = saleOrderItem.getSo2ShdQty();
        this.po2Uom = saleOrderItem.getSo2Uom();
        this.po2Uprice = saleOrderItem.getSo2Uprice();
        this.po2ItCatNoSuffix = saleOrderItem.getSo2ItCatNoSuffix();
    }

    public BigDecimal getPo2Amt() {
        return po2Amt;
    }

    public void setPo2Amt(BigDecimal po2Amt) {
        this.po2Amt = po2Amt;
    }

    public String getPo2Appendix() {
        return po2Appendix;
    }

    public void setPo2Appendix(String po2Appendix) {
        this.po2Appendix = po2Appendix;
    }

    public String getPo2Article() {
        return po2Article;
    }

    public void setPo2Article(String po2Article) {
        this.po2Article = po2Article;
    }

    public String getPo2Ean() {
        return po2Ean;
    }

    public void setPo2Ean(String po2Ean) {
        this.po2Ean = po2Ean;
    }

    public String getPo2ExpCtnPcs() {
        return po2ExpCtnPcs;
    }

    public void setPo2ExpCtnPcs(String po2ExpCtnPcs) {
        this.po2ExpCtnPcs = po2ExpCtnPcs;
    }

    public String getPo2ExpUom() {
        return po2ExpUom;
    }

    public void setPo2ExpUom(String po2ExpUom) {
        this.po2ExpUom = po2ExpUom;
    }

    public String getPo2InnCtnPcs() {
        return po2InnCtnPcs;
    }

    public void setPo2InnCtnPcs(String po2InnCtnPcs) {
        this.po2InnCtnPcs = po2InnCtnPcs;
    }

    public String getPo2InnUom() {
        return po2InnUom;
    }

    public void setPo2InnUom(String po2InnUom) {
        this.po2InnUom = po2InnUom;
    }

    public String getPo2ItCat() {
        return po2ItCat;
    }

    public void setPo2ItCat(String po2ItCat) {
        this.po2ItCat = po2ItCat;
    }

    public String getPo2ItDetails() {
        return po2ItDetails;
    }

    public void setPo2ItDetails(String po2ItDetails) {
        this.po2ItDetails = po2ItDetails;
    }

    public String getPo2ItName() {
        return po2ItName;
    }

    public void setPo2ItName(String po2ItName) {
        this.po2ItName = po2ItName;
    }

    public String getPo2ItNo() {
        return po2ItNo;
    }

    public void setPo2ItNo(String po2ItNo) {
        this.po2ItNo = po2ItNo;
    }

    public String getPo2ItSuffix() {
        return po2ItSuffix;
    }

    public void setPo2ItSuffix(String po2ItSuffix) {
        this.po2ItSuffix = po2ItSuffix;
    }

    public String getPo2No() {
        return po2No;
    }

    public void setPo2No(String po2No) {
        this.po2No = po2No;
    }

    public BigDecimal getPo2OrdQty() {
        return po2OrdQty;
    }

    public void setPo2OrdQty(BigDecimal po2OrdQty) {
        this.po2OrdQty = po2OrdQty;
    }

    public String getPo2Remk() {
        return po2Remk;
    }

    public void setPo2Remk(String po2Remk) {
        this.po2Remk = po2Remk;
    }

    public BigDecimal getPo2ShdQty() {
        return po2ShdQty;
    }

    public void setPo2ShdQty(BigDecimal po2ShdQty) {
        this.po2ShdQty = po2ShdQty;
    }

    public String getPo2SoNo() {
        return po2SoNo;
    }

    public void setPo2SoNo(String po2SoNo) {
        this.po2SoNo = po2SoNo;
    }

    public String getPo2Uom() {
        return po2Uom;
    }

    public void setPo2Uom(String po2Uom) {
        this.po2Uom = po2Uom;
    }

    public BigDecimal getPo2Uprice() {
        return po2Uprice;
    }

    public void setPo2Uprice(BigDecimal po2Uprice) {
        this.po2Uprice = po2Uprice;
    }

    public String getPo2ItCatNoSuffix() {
        return po2ItCatNoSuffix;
    }

    public void setPo2ItCatNoSuffix(String po2ItCatNoSuffix) {
        this.po2ItCatNoSuffix = po2ItCatNoSuffix;
    }

    public Integer getId() {
        return id;
    }
}
