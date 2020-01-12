package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:47
 * @Package: sop.persistence.beans
 */

public class Factory extends BaseBean {
    /**
     * table supp
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String suCode;
    private String suName;
    private String suChn;
    private String suConPer;
    private String suAdd;
    private String suTel;
    private String suFax;
    private String suPayTm;
    private BigDecimal suCredit;
    private String suType;
    /*这中间的field在表中都是空null，应该是不用了。*/
    private String suConPer1;
    private String suConPer2;
    private String suChnAdd;
    private String suTel1Cty;
    private String suTel1No;
    private String suTel2Cty;
    private String suTel2No;
    private String suFax1Cty;
    private String suFax1No;
    private String suFax2Cty;
    private String suFax2No;
    private String suPterm;
    private String suCcy;
    private String suCrBal;
    private String suCrUsed;
    private String suGlInfSettle;
    /*这中间的field在表中都是空null，应该是不用了。*/
    private String suWebsiteUrl;
    private String suFSCCertCode;
    private String suFSCValidFm;
    private String suFSCValidTo;
    private String suBSCICertCode;
    private String suBSCIValidFm;
    private String suBSCIValidTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuCode() {
        return suCode;
    }

    public void setSuCode(String suCode) {
        this.suCode = suCode;
    }

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getSuChn() {
        return suChn;
    }

    public void setSuChn(String suChn) {
        this.suChn = suChn;
    }

    public String getSuConPer() {
        return suConPer;
    }

    public void setSuConPer(String suConPer) {
        this.suConPer = suConPer;
    }

    public String getSuAdd() {
        return suAdd;
    }

    public void setSuAdd(String suAdd) {
        this.suAdd = suAdd;
    }

    public String getSuTel() {
        return suTel;
    }

    public void setSuTel(String suTel) {
        this.suTel = suTel;
    }

    public String getSuFax() {
        return suFax;
    }

    public void setSuFax(String suFax) {
        this.suFax = suFax;
    }

    public String getSuPayTm() {
        return suPayTm;
    }

    public void setSuPayTm(String suPayTm) {
        this.suPayTm = suPayTm;
    }

    public BigDecimal getSuCredit() {
        return suCredit;
    }

    public void setSuCredit(BigDecimal suCredit) {
        this.suCredit = suCredit;
    }

    public String getSuType() {
        return suType;
    }

    public void setSuType(String suType) {
        this.suType = suType;
    }

    public String getSuCrUsed() {
        return suCrUsed;
    }

    public void setSuCrUsed(String suCrUsed) {
        this.suCrUsed = suCrUsed;
    }

    public String getSuConPer1() {
        return suConPer1;
    }

    public void setSuConPer1(String suConPer1) {
        this.suConPer1 = suConPer1;
    }

    public String getSuConPer2() {
        return suConPer2;
    }

    public void setSuConPer2(String suConPer2) {
        this.suConPer2 = suConPer2;
    }

    public String getSuChnAdd() {
        return suChnAdd;
    }

    public void setSuChnAdd(String suChnAdd) {
        this.suChnAdd = suChnAdd;
    }

    public String getSuTel1Cty() {
        return suTel1Cty;
    }

    public void setSuTel1Cty(String suTel1Cty) {
        this.suTel1Cty = suTel1Cty;
    }

    public String getSuTel1No() {
        return suTel1No;
    }

    public void setSuTel1No(String suTel1No) {
        this.suTel1No = suTel1No;
    }

    public String getSuTel2Cty() {
        return suTel2Cty;
    }

    public void setSuTel2Cty(String suTel2Cty) {
        this.suTel2Cty = suTel2Cty;
    }

    public String getSuTel2No() {
        return suTel2No;
    }

    public void setSuTel2No(String suTel2No) {
        this.suTel2No = suTel2No;
    }

    public String getSuFax1Cty() {
        return suFax1Cty;
    }

    public void setSuFax1Cty(String suFax1Cty) {
        this.suFax1Cty = suFax1Cty;
    }

    public String getSuFax1No() {
        return suFax1No;
    }

    public void setSuFax1No(String suFax1No) {
        this.suFax1No = suFax1No;
    }

    public String getSuFax2Cty() {
        return suFax2Cty;
    }

    public void setSuFax2Cty(String suFax2Cty) {
        this.suFax2Cty = suFax2Cty;
    }

    public String getSuFax2No() {
        return suFax2No;
    }

    public void setSuFax2No(String suFax2No) {
        this.suFax2No = suFax2No;
    }

    public String getSuPterm() {
        return suPterm;
    }

    public void setSuPterm(String suPterm) {
        this.suPterm = suPterm;
    }

    public String getSuCcy() {
        return suCcy;
    }

    public void setSuCcy(String suCcy) {
        this.suCcy = suCcy;
    }

    public String getSuCrBal() {
        return suCrBal;
    }

    public void setSuCrBal(String suCrBal) {
        this.suCrBal = suCrBal;
    }

    public String getSuGlInfSettle() {
        return suGlInfSettle;
    }

    public void setSuGlInfSettle(String suGlInfSettle) {
        this.suGlInfSettle = suGlInfSettle;
    }

    public String getSuWebsiteUrl() {
        return suWebsiteUrl;
    }

    public void setSuWebsiteUrl(String suWebsiteUrl) {
        this.suWebsiteUrl = suWebsiteUrl;
    }

    public String getSuFSCCertCode() {
        return suFSCCertCode;
    }

    public void setSuFSCCertCode(String suFSCCertCode) {
        this.suFSCCertCode = suFSCCertCode;
    }

    public String getSuFSCValidFm() {
        return suFSCValidFm;
    }

    public void setSuFSCValidFm(String suFSCValidFm) {
        this.suFSCValidFm = suFSCValidFm;
    }

    public String getSuFSCValidTo() {
        return suFSCValidTo;
    }

    public void setSuFSCValidTo(String suFSCValidTo) {
        this.suFSCValidTo = suFSCValidTo;
    }

    public String getSuBSCICertCode() {
        return suBSCICertCode;
    }

    public void setSuBSCICertCode(String suBSCICertCode) {
        this.suBSCICertCode = suBSCICertCode;
    }

    public String getSuBSCIValidFm() {
        return suBSCIValidFm;
    }

    public void setSuBSCIValidFm(String suBSCIValidFm) {
        this.suBSCIValidFm = suBSCIValidFm;
    }

    public String getSuBSCIValidTo() {
        return suBSCIValidTo;
    }

    public void setSuBSCIValidTo(String suBSCIValidTo) {
        this.suBSCIValidTo = suBSCIValidTo;
    }
}
