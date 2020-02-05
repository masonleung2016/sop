package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:52
 * @Package: sop.persistence.beans
 */


public class OfferSheetItem extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private Integer id;

    private String coCode;

    private String off2ShNoPfix;

    private String off2ShNo;

    private String off2ShNoPfixNo;

    private String off2ItCat;

    private String off2ItNo;

    private String off2ItSuffix;

    private String off2DescOfferSh;

    private String off2ItName;

    private String off2ItRemark;

    private String off2CertOrigin;

    private String off2FormA;

    private BigDecimal off2CnfPrice;

    private String off2FobPort;

    private String off2CnfPort;

    private String off2CurrCode;

    private BigDecimal off2FobPrice;

    private BigDecimal off2FobCost;

    private BigDecimal off2MarkupRate;

    private BigDecimal off2FobPriceB;

    private BigDecimal off2MarkupRateB;

    private String off2PriceTradeUnit;

    private BigDecimal off2Price;

    private String off2PricePort;

    private BigDecimal off2Moq;

    private String off2MoqUom;

    private String off2ItCatNoSuffix;

    private BigDecimal off2FobCostB;

    private Integer off2MoqA;

    private Integer off2MoqB;
    
    public OfferSheetItem() {

    }
    public OfferSheetItem(ItemMaster itemMaster) {
        this.off2ItCat = itemMaster.getItCat();
        this.off2ItNo = itemMaster.getItNo();
        this.off2ItSuffix = itemMaster.getItSuffix();
        this.off2ItCatNoSuffix = this.off2ItCat + this.off2ItNo + this.off2ItSuffix;
        this.off2ItName = itemMaster.getItName();
        this.off2DescOfferSh = itemMaster.getDetailData();
        this.off2CurrCode = itemMaster.getItCurr();
        this.off2FobCost = itemMaster.getItFtyFobCost();
        this.off2FobCostB = itemMaster.getItFtyFobCostB();
        this.off2MoqA = itemMaster.getItMoqA();
        this.off2MoqB = itemMaster.getItMoqB();
        this.off2FobPort = itemMaster.getItFtyFobPort();
    }
    public OfferSheetItem(ItemMaster itemMaster, OfferSheet currentOfferSheet) {
        this.coCode = currentOfferSheet.getCoCode();
        this.off2ShNoPfix = currentOfferSheet.getOffShNoPfix();
        this.off2ShNo = currentOfferSheet.getOffShNo();
        this.off2ItCat = itemMaster.getItCat();
        this.off2ItNo = itemMaster.getItNo();
        this.off2ItSuffix = itemMaster.getItSuffix();
        this.off2ItName = itemMaster.getItName();
    }
    public OfferSheetItem(OfferSheet currentOfferSheet) {
        this.coCode = currentOfferSheet.getCoCode();
        this.off2ShNoPfix = currentOfferSheet.getOffShNoPfix();
        this.off2ShNo = currentOfferSheet.getOffShNo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getOff2ShNoPfix() {
        return off2ShNoPfix;
    }

    public void setOff2ShNoPfix(String off2ShNoPfix) {
        this.off2ShNoPfix = off2ShNoPfix;
    }

    public String getOff2ShNo() {
        return off2ShNo;
    }

    public void setOff2ShNo(String off2ShNo) {
        this.off2ShNo = off2ShNo;
    }

    public String getOff2ItCat() {
        return off2ItCat;
    }

    public void setOff2ItCat(String off2ItCat) {
        this.off2ItCat = off2ItCat;
    }

    public String getOff2ItNo() {
        return off2ItNo;
    }

    public void setOff2ItNo(String off2ItNo) {
        this.off2ItNo = off2ItNo;
    }

    public String getOff2ItSuffix() {
        return off2ItSuffix;
    }

    public void setOff2ItSuffix(String off2ItSuffix) {
        this.off2ItSuffix = off2ItSuffix;
    }

    public String getOff2DescOfferSh() {
        return off2DescOfferSh;
    }

    public void setOff2DescOfferSh(String off2DescOfferSh) {
        this.off2DescOfferSh = off2DescOfferSh;
    }

    public String getOff2ItName() {
        return off2ItName;
    }

    public void setOff2ItName(String off2ItName) {
        this.off2ItName = off2ItName;
    }

    public String getOff2ItRemark() {
        return off2ItRemark;
    }

    public void setOff2ItRemark(String off2ItRemark) {
        this.off2ItRemark = off2ItRemark;
    }

    public String getOff2CertOrigin() {
        return off2CertOrigin;
    }

    public void setOff2CertOrigin(String off2CertOrigin) {
        this.off2CertOrigin = off2CertOrigin;
    }

    public String getOff2FormA() {
        return off2FormA;
    }

    public void setOff2FormA(String off2FormA) {
        this.off2FormA = off2FormA;
    }

    public BigDecimal getOff2CnfPrice() {
        return off2CnfPrice;
    }

    public void setOff2CnfPrice(BigDecimal off2CnfPrice) {
        this.off2CnfPrice = off2CnfPrice;
    }

    public String getOff2FobPort() {
        return off2FobPort;
    }

    public void setOff2FobPort(String off2FobPort) {
        this.off2FobPort = off2FobPort;
    }

    public String getOff2CnfPort() {
        return off2CnfPort;
    }

    public void setOff2CnfPort(String off2CnfPort) {
        this.off2CnfPort = off2CnfPort;
    }

    public String getOff2CurrCode() {
        return off2CurrCode;
    }

    public void setOff2CurrCode(String off2CurrCode) {
        this.off2CurrCode = off2CurrCode;
    }

    public BigDecimal getOff2FobPrice() {
        return off2FobPrice;
    }

    public void setOff2FobPrice(BigDecimal off2FobPrice) {
        this.off2FobPrice = off2FobPrice;
    }

    public BigDecimal getOff2FobCost() {
        return off2FobCost;
    }

    public void setOff2FobCost(BigDecimal off2FobCost) {
        this.off2FobCost = off2FobCost;
    }

    public BigDecimal getOff2MarkupRate() {
        return off2MarkupRate;
    }

    public void setOff2MarkupRate(BigDecimal off2MarkupRate) {
        this.off2MarkupRate = off2MarkupRate;
    }

    public BigDecimal getOff2FobPriceB() {
        return off2FobPriceB;
    }

    public void setOff2FobPriceB(BigDecimal off2FobPriceB) {
        this.off2FobPriceB = off2FobPriceB;
    }

    public BigDecimal getOff2MarkupRateB() {
        return off2MarkupRateB;
    }

    public void setOff2MarkupRateB(BigDecimal off2MarkupRateB) {
        this.off2MarkupRateB = off2MarkupRateB;
    }

    public String getOff2PriceTradeUnit() {
        return off2PriceTradeUnit;
    }

    public void setOff2PriceTradeUnit(String off2PriceTradeUnit) {
        this.off2PriceTradeUnit = off2PriceTradeUnit;
    }

    public BigDecimal getOff2Price() {
        return off2Price;
    }

    public void setOff2Price(BigDecimal off2Price) {
        this.off2Price = off2Price;
    }

    public String getOff2PricePort() {
        return off2PricePort;
    }

    public void setOff2PricePort(String off2PricePort) {
        this.off2PricePort = off2PricePort;
    }

    public BigDecimal getOff2Moq() {
        return off2Moq;
    }

    public void setOff2Moq(BigDecimal off2Moq) {
        this.off2Moq = off2Moq;
    }

    public String getOff2MoqUom() {
        return off2MoqUom;
    }

    public void setOff2MoqUom(String off2MoqUom) {
        this.off2MoqUom = off2MoqUom;
    }

    public String getOff2ItCatNoSuffix() {
        return off2ItCatNoSuffix;
    }

    public void setOff2ItCatNoSuffix(String off2ItCatNoSuffix) {
        this.off2ItCatNoSuffix = off2ItCatNoSuffix;
    }

    public BigDecimal getOff2FobCostB() {
        return off2FobCostB;
    }

    public void setOff2FobCostB(BigDecimal off2FobCostB) {
        this.off2FobCostB = off2FobCostB;
    }

    public Integer getOff2MoqA() {
        return off2MoqA;
    }

    public void setOff2MoqA(Integer off2MoqA) {
        this.off2MoqA = off2MoqA;
    }

    public Integer getOff2MoqB() {
        return off2MoqB;
    }

    public void setOff2MoqB(Integer off2MoqB) {
        this.off2MoqB = off2MoqB;
    }

    public String getOff2ShNoPfixNo() {
        return off2ShNoPfixNo;
    }

    public void setOff2ShNoPfixNo(String off2ShNoPfixNo) {
        this.off2ShNoPfixNo = off2ShNoPfixNo;
    }
}
