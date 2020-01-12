package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:06
 * @Package: sop.persistence.beans
 */


public class SaleOrderItem extends BaseBean {

    /**
     * sotx2
     */
    private static final long serialVersionUID = -185248557316796065L;
    private Integer id;
    /**
     * so2_no
     */
    private String so2No;
    /**
     * so2_off_sh_no_pfix
     */
    private String so2OffShNoPfix;
    /**
     * so2_off_sh_no
     */
    private String so2OffShNo;
    /**
     * so2_it_cat
     */
    private String so2ItCat;
    /**
     * so2_it_no
     */
    private String so2ItNo;
    /**
     * so2_it_suffix
     */
    private String so2ItSuffix;
    /**
     * so2_it_name
     */
    private String so2ItName;
    /**
     * so2_ord_qty
     */
    private BigDecimal so2OrdQty;
    /**
     * so2_shd_qty
     */
    private BigDecimal so2ShdQty;
    /**
     * so2_uom
     */
    private String so2Uom;
    /**
     * so2_uprice
     */
    private BigDecimal so2Uprice;
    /**
     * so2_amt
     */
    private BigDecimal so2Amt;
    /**
     * so2_remk
     */
    private String so2Remk;
    /**
     * so2_it_details
     */
    private String so2ItDetails;
    /**
     * so2_cu_it_no
     */
    private String so2CuItNo;
    /**
     * so2_ean
     */
    private String so2Ean;
    private String so2ItCatNoSuffix;
    public SaleOrderItem() {

    }
    public SaleOrderItem(ItemMaster itemMaster) {
        this.so2OffShNoPfix = "0";
        this.so2OffShNo = "0";
        this.so2ItCat = itemMaster.getItCat();
        this.so2ItNo = itemMaster.getItNo();
        this.so2ItSuffix = itemMaster.getItSuffix();
        this.so2ItCatNoSuffix = this.so2ItCat + this.so2ItNo + this.so2ItSuffix;
        this.so2ItName = itemMaster.getItName();
        this.so2Uom = itemMaster.getItUom();
        this.so2ItDetails = itemMaster.getDetailData();
    }

    public SaleOrderItem(OfferSheetItem offerSheetItem) {
        this.so2OffShNoPfix = offerSheetItem.getOff2ShNoPfix();
        this.so2OffShNo = offerSheetItem.getOff2ShNo();
        this.so2ItCat = offerSheetItem.getOff2ItCat();
        this.so2ItNo = offerSheetItem.getOff2ItNo();
        this.so2ItSuffix = offerSheetItem.getOff2ItSuffix();
        this.so2ItName = offerSheetItem.getOff2ItName();
        this.so2ItDetails = offerSheetItem.getOff2DescOfferSh();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getSo2No() {
        return so2No;
    }

    public void setSo2No(String so2No) {
        this.so2No = so2No;
    }

    public String getSo2OffShNoPfix() {
        return so2OffShNoPfix;
    }

    public void setSo2OffShNoPfix(String so2OffShNoPfix) {
        this.so2OffShNoPfix = so2OffShNoPfix;
    }

    public String getSo2OffShNo() {
        return so2OffShNo;
    }

    public void setSo2OffShNo(String so2OffShNo) {
        this.so2OffShNo = so2OffShNo;
    }

    public String getSo2ItCat() {
        return so2ItCat;
    }

    public void setSo2ItCat(String so2ItCat) {
        this.so2ItCat = so2ItCat;
    }

    public String getSo2ItNo() {
        return so2ItNo;
    }

    public void setSo2ItNo(String so2ItNo) {
        this.so2ItNo = so2ItNo;
    }

    public String getSo2ItSuffix() {
        return so2ItSuffix;
    }

    public void setSo2ItSuffix(String so2ItSuffix) {
        this.so2ItSuffix = so2ItSuffix;
    }

    public String getSo2ItName() {
        return so2ItName;
    }

    public void setSo2ItName(String so2ItName) {
        this.so2ItName = so2ItName;
    }

    public BigDecimal getSo2OrdQty() {
        return so2OrdQty;
    }

    public void setSo2OrdQty(BigDecimal so2OrdQty) {
        this.so2OrdQty = so2OrdQty;
    }

    public BigDecimal getSo2ShdQty() {
        return so2ShdQty;
    }

    public void setSo2ShdQty(BigDecimal so2ShdQty) {
        this.so2ShdQty = so2ShdQty;
    }

    public String getSo2Uom() {
        return so2Uom;
    }

    public void setSo2Uom(String so2Uom) {
        this.so2Uom = so2Uom;
    }

    public BigDecimal getSo2Uprice() {
        return so2Uprice;
    }

    public void setSo2Uprice(BigDecimal so2Uprice) {
        this.so2Uprice = so2Uprice;
    }

    public BigDecimal getSo2Amt() {
        return so2Amt;
    }

    public void setSo2Amt(BigDecimal so2Amt) {
        this.so2Amt = so2Amt;
    }

    public String getSo2Remk() {
        return so2Remk;
    }

    public void setSo2Remk(String so2Remk) {
        this.so2Remk = so2Remk;
    }

    public String getSo2ItDetails() {
        return so2ItDetails;
    }

    public void setSo2ItDetails(String so2ItDetails) {
        this.so2ItDetails = so2ItDetails;
    }

    public String getSo2CuItNo() {
        return so2CuItNo;
    }

    public void setSo2CuItNo(String so2CuItNo) {
        this.so2CuItNo = so2CuItNo;
    }

    public String getSo2Ean() {
        return so2Ean;
    }

    public void setSo2Ean(String so2Ean) {
        this.so2Ean = so2Ean;
    }

    public String getSo2ItCatNoSuffix() {
        return so2ItCatNoSuffix;
    }

    public void setSo2ItCatNoSuffix(String so2ItCatNoSuffix) {
        this.so2ItCatNoSuffix = so2ItCatNoSuffix;
    }
}
