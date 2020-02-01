package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:50
 * @Package: sop.persistence.beans
 */
public class ItemMaster extends BaseBean {

    /**
     * table itemmasters
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * it_cat_no_suffix
     */
    private String itCatNoSuffix;

    /**
     * it_cat
     */
    private String itCat;

    /**
     * it_no
     */
    private String itNo;

    /**
     * it_suffix
     */
    private String itSuffix;

    /**
     * it_name
     */
    private String itName;

    /**
     * it_uom
     */
    private String itUom;
	
    /**
     * it_del_time
     */
    private String itDelTime;
	
    /**
     * it_base_price
     */
    private BigDecimal itBasePrice;
	
    /**
     * it_curr
     */
    private String itCurr;
	
    /**
     * it_handle
     */
    private String itHandle;
	
    /**
     * it_fty_item_no
     */
    private String itFtyItemNo;
	
    /**
     * it_desc_of_sh
     */
    private String itDescOfSh;
	
    /**
     * it_rmk
     */
    private String itRmk;
	
    /**
     * it_pkg_detail
     */
    private String itPkgDetail;
	
    /**
     * it_inn_ctn_uom
     */
    private String itInnCtnUom;
	
    /**
     * it_pkg_inn_pcs
     */
    private String itPkgInnPcs;
	
    /**
     * it_exp_ctn_1_l_cm
     * = it_pkg_l_cm
     */
    private BigDecimal itExpCtn1LCm;
	
    /**
     * it_exp_ctn_1_w_cm
     * = it_pkg_w_cm
     */
    private BigDecimal itExpCtn1WCm;
	
    /**
     * it_exp_ctn_1_h_cm
     * = it_pkg_h_cm
     */
    private BigDecimal itExpCtn1HCm;

    /**
     * it_pkg_l_cm
     *//*
	private BigDecimal itPkgLCm;

	*//**
     * it_pkg_w_cm
     *//*
	private BigDecimal itPkgWCm;


	*//**
     * it_pkg_h_cm
     *//*
	private BigDecimal itPkgHCm;
	*/
    /**
     * it_exp_ctn_uom
     */
    private String itExpCtnUom;
	
    /**
     * it_pkg_exp_pcs
     */
    private String itPkgExpPcs;
	
    /**
     * it_cbm_total
     */
    private BigDecimal itCbmTotal;
	
    /**
     * it_exp_ctn_1_cbm
     * = it_cbm_total
     */
    private BigDecimal itExpCtn1Cbm;
	
    /**
     * it_pkg_nw_kg
     */
    private BigDecimal itPkgNwKg;
	
    /**
     * it_exp_ctn_1_nw
     */
    private BigDecimal itExpCtn1Nw;
	
    /**
     * it_pkg_20_qty
     */
    private BigDecimal itPkg20Qty;
	
    /**
     * it_pkg_40_qty
     */
    private BigDecimal itPkg40Qty;
	
    /**
     * it_pkg_40HQ_qty
     */
    private BigDecimal itPkg40HQQty;
	
    /**
     * it_pkg_gw_kg
     */
    private BigDecimal itPkgGwKg;
	
    /**
     * it_exp_ctn_1_gw
     * = it_pkg_gw_kg
     */
    private BigDecimal itExpCtn1Gw;
	
    /**
     * it_fty_fob_cost
     */
    private BigDecimal itFtyFobCost;
	
    /**
     * it_fty_fob_port
     */
    private String itFtyFobPort;
	
    /**
     * it_fty_code
     */
    private String itFtyCode;
	
    /**
     * it_fty_cost_last_update
     */
    private Date itFtyCostLastUpdate;
	
    /**
     * it_fty_name
     */
    private String itFtyName;
	
    /**
     * it_pic_name_jpg1
     */
    private String itPicNameJpg1;
	
    /**
     * it_pic_name_jpg2
     */
    private String itPicNameJpg2;
	
    /**
     * it_image_1
     */
    private String itImage1;
	
    /**
     * it_image_2
     */
    private String itImage2;
	
    /**
     * it_image_3
     */
    private String itImage3;
	
    /**
     * it_image_4
     */
    private String itImage4;
	
    private String itImage1Encode;
	
    private String itImage2Encode;
	
    private String itImage3Encode;
	
    private String itImage4Encode;
	
    /**
     * it_desc_fty_po
     */
    private String itDescFtyPo;
	
    private BigDecimal itPkgLCm0;
	
    private BigDecimal itPkgWCm0;
	
    private BigDecimal itPkgHCm0;
	
    private BigDecimal itPkgLCm1;
	
    private BigDecimal itPkgWCm1;
	
    private BigDecimal itPkgHCm1;
	
    private BigDecimal itPkgLCm2;
	
    private BigDecimal itPkgWCm2;
	
    private BigDecimal itPkgHCm2;
	
    private BigDecimal itPkgLCm3;
	
    private BigDecimal itPkgWCm3;
	
    private BigDecimal itPkgHCm3;
	
    private BigDecimal itPkgLCm4;
	
    private BigDecimal itPkgWCm4;
	
    private BigDecimal itPkgHCm4;
	
    private BigDecimal itPkgLCm5;
	
    private BigDecimal itPkgWCm5;
	
    private BigDecimal itPkgHCm5;
	
    private String itPkgIndInnPcs;
	
    private String itIndInnCtnUom;
	
    private String itIndPacking;
	
    private String itIndLabel;
	
    private String itInnPacking;
	
    private String itInnLabel;
	
    private String itExpCtnPacking;
	
    private String itExpCtnLabel;
	
    /**
     * fk_item_type
     */
    private Integer fkItemType;
    ////////////////////
    /*
     * 拓展属性
     */
    //////////////
    /**
     * fk_template
     */
    private Integer fkTemplate;
	
    /**
     * detail_data
     */
    private String detailData;

    /**
     * sub_category 子类
     */
    //private String subCategory;
    /**
     * label_eng
     */
    private String labelEng;
	
    /**
     * label_chn
     */
    private String labelChn;
	
    /**
     * importdatetime
     */
    private Date importdatetime;
	
    /**
     * item_eng_name
     */
    private String itemEngName;
	
    /**
     * item_chn_name
     */
    private String itemChnName;
	
    /**
     * template_name
     */
    private String templateName;
	
    /**
     * eng_template_name
     */
    private String engTemplateName;
	
    /**
     * item_type
     */
    private String itemType;
	
    /**
     * part_pic
     */
    private String partPic;
	
    /**
     * it_fty_fob_cost_b
     */
    private BigDecimal itFtyFobCostB;
	
    /**
     * it_moq_a
     */
    private Integer itMoqA;
	
    /**
     * it_moq_b
     */
    private Integer itMoqB;
	
    private String shippingMark;

    public ItemMaster() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItCatNoSuffix() {
        return itCatNoSuffix;
    }

    public void setItCatNoSuffix(String itCatNoSuffix) {
        this.itCatNoSuffix = itCatNoSuffix;
    }

    public String getItCat() {
        return itCat;
    }

    public void setItCat(String itCat) {
        this.itCat = itCat;
    }

    public String getItNo() {
        return itNo;
    }

    public void setItNo(String itNo) {
        this.itNo = itNo;
    }

    public String getItSuffix() {
        return itSuffix;
    }

    public void setItSuffix(String itSuffix) {
        this.itSuffix = itSuffix;
    }

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
    }

    public String getItUom() {
        return itUom;
    }

    public void setItUom(String itUom) {
        this.itUom = itUom;
    }

    public String getItDelTime() {
        return itDelTime;
    }

    public void setItDelTime(String itDelTime) {
        this.itDelTime = itDelTime;
    }

    public BigDecimal getItBasePrice() {
        return itBasePrice;
    }

    public void setItBasePrice(BigDecimal itBasePrice) {
        this.itBasePrice = itBasePrice;
    }

    public String getItCurr() {
        return itCurr;
    }

    public void setItCurr(String itCurr) {
        this.itCurr = itCurr;
    }

    public String getItHandle() {
        return itHandle;
    }

    public void setItHandle(String itHandle) {
        this.itHandle = itHandle;
    }

    public String getItFtyItemNo() {
        return itFtyItemNo;
    }

    public void setItFtyItemNo(String itFtyItemNo) {
        this.itFtyItemNo = itFtyItemNo;
    }

    public String getItDescOfSh() {
        return itDescOfSh;
    }

    public void setItDescOfSh(String itDescOfSh) {
        this.itDescOfSh = itDescOfSh;
    }

    public String getItRmk() {
        return itRmk;
    }

    public void setItRmk(String itRmk) {
        this.itRmk = itRmk;
    }

    public String getItPkgDetail() {
        return itPkgDetail;
    }

    public void setItPkgDetail(String itPkgDetail) {
        this.itPkgDetail = itPkgDetail;
    }

    public String getItInnCtnUom() {
        return itInnCtnUom;
    }

    public void setItInnCtnUom(String itInnCtnUom) {
        this.itInnCtnUom = itInnCtnUom;
    }

    public String getItPkgInnPcs() {
        return itPkgInnPcs;
    }

    public void setItPkgInnPcs(String itPkgInnPcs) {
        this.itPkgInnPcs = itPkgInnPcs;
    }

    /*
        public BigDecimal getItPkgLCm() {
            return itPkgLCm;
        }

        public void setItPkgLCm(BigDecimal itPkgLCm) {
            this.itPkgLCm = itPkgLCm;
        }
    */
    public BigDecimal getItExpCtn1LCm() {
        return itExpCtn1LCm;
    }

/*	public BigDecimal getItPkgWCm() {
		return itPkgWCm;
	}

	public void setItPkgWCm(BigDecimal itPkgWCm) {
		this.itPkgWCm = itPkgWCm;
	}*/

    public void setItExpCtn1LCm(BigDecimal itExpCtn1LCm) {
        this.itExpCtn1LCm = itExpCtn1LCm;
    }

    public BigDecimal getItExpCtn1WCm() {
        return itExpCtn1WCm;
    }

/*	public BigDecimal getItPkgHCm() {
		return itPkgHCm;
	}

	public void setItPkgHCm(BigDecimal itPkgHCm) {
		this.itPkgHCm = itPkgHCm;
	}*/

    public void setItExpCtn1WCm(BigDecimal itExpCtn1WCm) {
        this.itExpCtn1WCm = itExpCtn1WCm;
    }

    public BigDecimal getItExpCtn1HCm() {
        return itExpCtn1HCm;
    }

    public void setItExpCtn1HCm(BigDecimal itExpCtn1HCm) {
        this.itExpCtn1HCm = itExpCtn1HCm;
    }

    public String getItExpCtnUom() {
        return itExpCtnUom;
    }

    public void setItExpCtnUom(String itExpCtnUom) {
        this.itExpCtnUom = itExpCtnUom;
    }

    public String getItPkgExpPcs() {
        return itPkgExpPcs;
    }

    public void setItPkgExpPcs(String itPkgExpPcs) {
        this.itPkgExpPcs = itPkgExpPcs;
    }

    public BigDecimal getItCbmTotal() {
        return itCbmTotal;
    }

    public void setItCbmTotal(BigDecimal itCbmTotal) {
        this.itCbmTotal = itCbmTotal;
    }

    public BigDecimal getItExpCtn1Cbm() {
        return itExpCtn1Cbm;
    }

    public void setItExpCtn1Cbm(BigDecimal itExpCtn1Cbm) {
        this.itExpCtn1Cbm = itExpCtn1Cbm;
    }

    public BigDecimal getItPkgNwKg() {
        return itPkgNwKg;
    }

    public void setItPkgNwKg(BigDecimal itPkgNwKg) {
        this.itPkgNwKg = itPkgNwKg;
    }

    public BigDecimal getItExpCtn1Nw() {
        return itExpCtn1Nw;
    }

    public void setItExpCtn1Nw(BigDecimal itExpCtn1Nw) {
        this.itExpCtn1Nw = itExpCtn1Nw;
    }

    public BigDecimal getItPkg20Qty() {
        return itPkg20Qty;
    }

    public void setItPkg20Qty(BigDecimal itPkg20Qty) {
        this.itPkg20Qty = itPkg20Qty;
    }

    public BigDecimal getItPkg40Qty() {
        return itPkg40Qty;
    }

    public void setItPkg40Qty(BigDecimal itPkg40Qty) {
        this.itPkg40Qty = itPkg40Qty;
    }

    public BigDecimal getItPkg40HQQty() {
        return itPkg40HQQty;
    }

    public void setItPkg40HQQty(BigDecimal itPkg40HQQty) {
        this.itPkg40HQQty = itPkg40HQQty;
    }

    public BigDecimal getItPkgGwKg() {
        return itPkgGwKg;
    }

    public void setItPkgGwKg(BigDecimal itPkgGwKg) {
        this.itPkgGwKg = itPkgGwKg;
    }

    public BigDecimal getItExpCtn1Gw() {
        return itExpCtn1Gw;
    }

    public void setItExpCtn1Gw(BigDecimal itExpCtn1Gw) {
        this.itExpCtn1Gw = itExpCtn1Gw;
    }

    public BigDecimal getItFtyFobCost() {
        return itFtyFobCost;
    }

    public void setItFtyFobCost(BigDecimal itFtyFobCost) {
        this.itFtyFobCost = itFtyFobCost;
    }

    public String getItFtyFobPort() {
        return itFtyFobPort;
    }

    public void setItFtyFobPort(String itFtyFobPort) {
        this.itFtyFobPort = itFtyFobPort;
    }

    public String getItFtyCode() {
        return itFtyCode;
    }

    public void setItFtyCode(String itFtyCode) {
        this.itFtyCode = itFtyCode;
    }

    public Date getItFtyCostLastUpdate() {
        return itFtyCostLastUpdate;
    }

    public void setItFtyCostLastUpdate(Date itFtyCostLastUpdate) {
        this.itFtyCostLastUpdate = itFtyCostLastUpdate;
    }

    public String getItFtyName() {
        return itFtyName;
    }

    public void setItFtyName(String itFtyName) {
        this.itFtyName = itFtyName;
    }

    public String getItPicNameJpg1() {
        return itPicNameJpg1;
    }

    public void setItPicNameJpg1(String itPicNameJpg1) {
        this.itPicNameJpg1 = itPicNameJpg1;
    }

    public String getItPicNameJpg2() {
        return itPicNameJpg2;
    }

    public void setItPicNameJpg2(String itPicNameJpg2) {
        this.itPicNameJpg2 = itPicNameJpg2;
    }

    public String getItImage1() {
        return itImage1;
    }

    public void setItImage1(String itImage1) {
        this.itImage1 = itImage1;
        this.itImage1Encode = Base64.encode(itImage1, "utf-8");
    }

    public String getItImage2() {
        return itImage2;
    }

    public void setItImage2(String itImage2) {
        this.itImage2 = itImage2;
        this.itImage2Encode = Base64.encode(itImage2, "utf-8");
    }

    public String getItImage3() {
        return itImage3;
    }

    public void setItImage3(String itImage3) {
        this.itImage3 = itImage3;
        this.itImage3Encode = Base64.encode(itImage3, "utf-8");
    }

    public String getItImage4() {
        return itImage4;
    }

    public void setItImage4(String itImage4) {
        this.itImage4 = itImage4;
        this.itImage4Encode = Base64.encode(itImage4, "utf-8");
    }

    public String getItDescFtyPo() {
        return itDescFtyPo;
    }

    public void setItDescFtyPo(String itDescFtyPo) {
        this.itDescFtyPo = itDescFtyPo;
    }

    public Integer getFkTemplate() {
        return fkTemplate;
    }

    public void setFkTemplate(Integer fkTemplate) {
        this.fkTemplate = fkTemplate;
    }

    public Integer getFkItemType() {
        return fkItemType;
    }

    public void setFkItemType(Integer fkItemType) {
        this.fkItemType = fkItemType;
    }

    public String getDetailData() {
        return detailData;
    }

    public void setDetailData(String detailData) {
        this.detailData = detailData;
    }

    public String getLabelEng() {
        return labelEng;
    }

    public void setLabelEng(String labelEng) {
        this.labelEng = labelEng;
    }

    public String getLabelChn() {
        return labelChn;
    }

    public void setLabelChn(String labelChn) {
        this.labelChn = labelChn;
    }

    public Date getImportdatetime() {
        return importdatetime;
    }

    public void setImportdatetime(Date importdatetime) {
        this.importdatetime = importdatetime;
    }

    public String getItemEngName() {
        return itemEngName;
    }

    public void setItemEngName(String itemEngName) {
        this.itemEngName = itemEngName;
    }

    public String getItemChnName() {
        return itemChnName;
    }

    public void setItemChnName(String itemChnName) {
        this.itemChnName = itemChnName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getPartPic() {
        return partPic;
    }

    public void setPartPic(String partPic) {
        this.partPic = partPic;
    }

    public String getItImage1Encode() {
        return itImage1Encode;
    }

    public void setItImage1Encode(String itImage1Encode) {
        this.itImage1Encode = itImage1Encode;
    }

    public String getItImage2Encode() {
        return itImage2Encode;
    }

    public void setItImage2Encode(String itImage2Encode) {
        this.itImage2Encode = itImage2Encode;
    }

    public String getItImage3Encode() {
        return itImage3Encode;
    }

    public void setItImage3Encode(String itImage3Encode) {
        this.itImage3Encode = itImage3Encode;
    }

    public String getItImage4Encode() {
        return itImage4Encode;
    }

    public void setItImage4Encode(String itImage4Encode) {
        this.itImage4Encode = itImage4Encode;
    }

    public BigDecimal getItFtyFobCostB() {
        return itFtyFobCostB;
    }

    public void setItFtyFobCostB(BigDecimal itFtyFobCostB) {
        this.itFtyFobCostB = itFtyFobCostB;
    }

    public Integer getItMoqA() {
        return itMoqA;
    }

    public void setItMoqA(Integer itMoqA) {
        this.itMoqA = itMoqA;
    }

    public Integer getItMoqB() {
        return itMoqB;
    }

    public void setItMoqB(Integer itMoqB) {
        this.itMoqB = itMoqB;
    }

    public String getEngTemplateName() {
        return engTemplateName;
    }

    public void setEngTemplateName(String engTemplateName) {
        this.engTemplateName = engTemplateName;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark(String shippingMark) {
        this.shippingMark = shippingMark;
    }

    public BigDecimal getItPkgLCm0() {
        return itPkgLCm0;
    }

    public void setItPkgLCm0(BigDecimal itPkgLCm0) {
        this.itPkgLCm0 = itPkgLCm0;
    }

    public BigDecimal getItPkgWCm0() {
        return itPkgWCm0;
    }

    public void setItPkgWCm0(BigDecimal itPkgWCm0) {
        this.itPkgWCm0 = itPkgWCm0;
    }

    public BigDecimal getItPkgHCm0() {
        return itPkgHCm0;
    }

    public void setItPkgHCm0(BigDecimal itPkgHCm0) {
        this.itPkgHCm0 = itPkgHCm0;
    }

    public BigDecimal getItPkgLCm1() {
        return itPkgLCm1;
    }

    public void setItPkgLCm1(BigDecimal itPkgLCm1) {
        this.itPkgLCm1 = itPkgLCm1;
    }

    public BigDecimal getItPkgWCm1() {
        return itPkgWCm1;
    }

    public void setItPkgWCm1(BigDecimal itPkgWCm1) {
        this.itPkgWCm1 = itPkgWCm1;
    }

    public BigDecimal getItPkgHCm1() {
        return itPkgHCm1;
    }

    public void setItPkgHCm1(BigDecimal itPkgHCm1) {
        this.itPkgHCm1 = itPkgHCm1;
    }

    public BigDecimal getItPkgLCm2() {
        return itPkgLCm2;
    }

    public void setItPkgLCm2(BigDecimal itPkgLCm2) {
        this.itPkgLCm2 = itPkgLCm2;
    }

    public BigDecimal getItPkgWCm2() {
        return itPkgWCm2;
    }

    public void setItPkgWCm2(BigDecimal itPkgWCm2) {
        this.itPkgWCm2 = itPkgWCm2;
    }

    public BigDecimal getItPkgHCm2() {
        return itPkgHCm2;
    }

    public void setItPkgHCm2(BigDecimal itPkgHCm2) {
        this.itPkgHCm2 = itPkgHCm2;
    }

    public BigDecimal getItPkgLCm3() {
        return itPkgLCm3;
    }

    public void setItPkgLCm3(BigDecimal itPkgLCm3) {
        this.itPkgLCm3 = itPkgLCm3;
    }

    public BigDecimal getItPkgWCm3() {
        return itPkgWCm3;
    }

    public void setItPkgWCm3(BigDecimal itPkgWCm3) {
        this.itPkgWCm3 = itPkgWCm3;
    }

    public BigDecimal getItPkgHCm3() {
        return itPkgHCm3;
    }

    public void setItPkgHCm3(BigDecimal itPkgHCm3) {
        this.itPkgHCm3 = itPkgHCm3;
    }

    public BigDecimal getItPkgLCm4() {
        return itPkgLCm4;
    }

    public void setItPkgLCm4(BigDecimal itPkgLCm4) {
        this.itPkgLCm4 = itPkgLCm4;
    }

    public BigDecimal getItPkgWCm4() {
        return itPkgWCm4;
    }

    public void setItPkgWCm4(BigDecimal itPkgWCm4) {
        this.itPkgWCm4 = itPkgWCm4;
    }

    public BigDecimal getItPkgHCm4() {
        return itPkgHCm4;
    }

    public void setItPkgHCm4(BigDecimal itPkgHCm4) {
        this.itPkgHCm4 = itPkgHCm4;
    }

    public BigDecimal getItPkgLCm5() {
        return itPkgLCm5;
    }

    public void setItPkgLCm5(BigDecimal itPkgLCm5) {
        this.itPkgLCm5 = itPkgLCm5;
    }

    public BigDecimal getItPkgWCm5() {
        return itPkgWCm5;
    }

    public void setItPkgWCm5(BigDecimal itPkgWCm5) {
        this.itPkgWCm5 = itPkgWCm5;
    }

    public BigDecimal getItPkgHCm5() {
        return itPkgHCm5;
    }

    public void setItPkgHCm5(BigDecimal itPkgHCm5) {
        this.itPkgHCm5 = itPkgHCm5;
    }

    public String getItPkgIndInnPcs() {
        return itPkgIndInnPcs;
    }

    public void setItPkgIndInnPcs(String itPkgIndInnPcs) {
        this.itPkgIndInnPcs = itPkgIndInnPcs;
    }

    public String getItIndInnCtnUom() {
        return itIndInnCtnUom;
    }

    public void setItIndInnCtnUom(String itIndInnCtnUom) {
        this.itIndInnCtnUom = itIndInnCtnUom;
    }

    public String getItIndPacking() {
        return itIndPacking;
    }

    public void setItIndPacking(String itIndPacking) {
        this.itIndPacking = itIndPacking;
    }

    public String getItIndLabel() {
        return itIndLabel;
    }

    public void setItIndLabel(String itIndLabel) {
        this.itIndLabel = itIndLabel;
    }

    public String getItInnPacking() {
        return itInnPacking;
    }

    public void setItInnPacking(String itInnPacking) {
        this.itInnPacking = itInnPacking;
    }

    public String getItInnLabel() {
        return itInnLabel;
    }

    public void setItInnLabel(String itInnLabel) {
        this.itInnLabel = itInnLabel;
    }

    public String getItExpCtnPacking() {
        return itExpCtnPacking;
    }

    public void setItExpCtnPacking(String itExpCtnPacking) {
        this.itExpCtnPacking = itExpCtnPacking;
    }

    public String getItExpCtnLabel() {
        return itExpCtnLabel;
    }

    public void setItExpCtnLabel(String itExpCtnLabel) {
        this.itExpCtnLabel = itExpCtnLabel;
    }
}
