package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:46
 * @Package: sop.persistence.beans
 */

public class Customer extends BaseBean {
    /**
     * table cust
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * cu_code
     */
    private String cuCode;
    /**
     * cu_name
     */
    private String cuName;
    /**
     * cu_chn
     */
    private String cuChn;
    /**
     * cu_con_per
     */
    private String cuConPer;
    /**
     * cu_add
     */
    private String cuAdd;
    /**
     * cu_tel
     */
    private String cuTel;
    /**
     * cu_fax
     */
    private String cuFax;
    /**
     * cu_pay_tm
     */
    private String cuPayTm;
    /**
     * cu_credit
     */
    private BigDecimal cuCredit;
    /*这中间的field在表中都是空null，应该是不用了。*/
    /**
     * cu_con_per1
     */
    private String cuConPer1;
    /**
     * cu_con_per2
     */
    private String cuConPer2;
    /**
     * cu_chn_add
     */
    private String cuChnAdd;
    /**
     * cu_tel1_cty
     */
    private String cuTel1Cty;
    /**
     * cu_tel1_no
     */
    private String cuTel1No;
    /**
     * cu_tel2_cty
     */
    private String cuTel2Cty;
    /**
     * cu_tel2_no
     */
    private String cuTel2No;
    /**
     * cu_fax1_cty
     */
    private String cuFax1Cty;
    /**
     * cu_fax1_no
     */
    private String cuFax1No;
    /**
     * cu_fax2_cty
     */
    private String cuFax2Cty;
    /**
     * cu_fax2_no
     */
    private String cuFax2No;
    /**
     * cu_sl_code
     */
    private String cuSlCode;
    /**
     * cu_bt_code
     */
    private String cuBtCode;
    /**
     * cu_pterm
     */
    private String cuPterm;
    /**
     * cu_ccy
     */
    private String cuCcy;
    /**
     * cu_cr_bal
     */
    private String cuCrBal;
    /**
     * cu_cr_used
     */
    private String cuCrUsed;
    /**
     * cu_type
     */
    private String cuType;

    /*这中间的field在表中都是空null，应该是不用了。*/
    /**
     * cu_gl_inf_si
     */
    private String cuGlInfSi;
    /**
     * cu_website_url
     */
    private String cuWebsiteUrl;
    /**
     * cu_FSC_cert_code
     */
    private String cuFSCCertCode;
    /**
     * cu_FSC_valid_fm
     */
    private Date cuFSCValidFm;
    /**
     * cu_FSC_valid_to
     */
    private Date cuFSCValidTo;

    private Map<String, CustAttn> custAttns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCuCode() {
        return cuCode;
    }

    public void setCuCode(String cuCode) {
        this.cuCode = cuCode;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getCuChn() {
        return cuChn;
    }

    public void setCuChn(String cuChn) {
        this.cuChn = cuChn;
    }

    public String getCuConPer() {
        return cuConPer;
    }

    public void setCuConPer(String cuConPer) {
        this.cuConPer = cuConPer;
    }

    public String getCuAdd() {
        return cuAdd;
    }

    public void setCuAdd(String cuAdd) {
        this.cuAdd = cuAdd;
    }

    public String getCuTel() {
        return cuTel;
    }

    public void setCuTel(String cuTel) {
        this.cuTel = cuTel;
    }

    public String getCuFax() {
        return cuFax;
    }

    public void setCuFax(String cuFax) {
        this.cuFax = cuFax;
    }

    public String getCuPayTm() {
        return cuPayTm;
    }

    public void setCuPayTm(String cuPayTm) {
        this.cuPayTm = cuPayTm;
    }

    public BigDecimal getCuCredit() {
        return cuCredit;
    }

    public void setCuCredit(BigDecimal cuCredit) {
        this.cuCredit = cuCredit;
    }

    public String getCuConPer1() {
        return cuConPer1;
    }

    public void setCuConPer1(String cuConPer1) {
        this.cuConPer1 = cuConPer1;
    }

    public String getCuConPer2() {
        return cuConPer2;
    }

    public void setCuConPer2(String cuConPer2) {
        this.cuConPer2 = cuConPer2;
    }

    public String getCuChnAdd() {
        return cuChnAdd;
    }

    public void setCuChnAdd(String cuChnAdd) {
        this.cuChnAdd = cuChnAdd;
    }

    public String getCuTel1Cty() {
        return cuTel1Cty;
    }

    public void setCuTel1Cty(String cuTel1Cty) {
        this.cuTel1Cty = cuTel1Cty;
    }

    public String getCuTel1No() {
        return cuTel1No;
    }

    public void setCuTel1No(String cuTel1No) {
        this.cuTel1No = cuTel1No;
    }

    public String getCuTel2Cty() {
        return cuTel2Cty;
    }

    public void setCuTel2Cty(String cuTel2Cty) {
        this.cuTel2Cty = cuTel2Cty;
    }

    public String getCuTel2No() {
        return cuTel2No;
    }

    public void setCuTel2No(String cuTel2No) {
        this.cuTel2No = cuTel2No;
    }

    public String getCuFax1Cty() {
        return cuFax1Cty;
    }

    public void setCuFax1Cty(String cuFax1Cty) {
        this.cuFax1Cty = cuFax1Cty;
    }

    public String getCuFax1No() {
        return cuFax1No;
    }

    public void setCuFax1No(String cuFax1No) {
        this.cuFax1No = cuFax1No;
    }

    public String getCuFax2Cty() {
        return cuFax2Cty;
    }

    public void setCuFax2Cty(String cuFax2Cty) {
        this.cuFax2Cty = cuFax2Cty;
    }

    public String getCuFax2No() {
        return cuFax2No;
    }

    public void setCuFax2No(String cuFax2No) {
        this.cuFax2No = cuFax2No;
    }

    public String getCuSlCode() {
        return cuSlCode;
    }

    public void setCuSlCode(String cuSlCode) {
        this.cuSlCode = cuSlCode;
    }

    public String getCuBtCode() {
        return cuBtCode;
    }

    public void setCuBtCode(String cuBtCode) {
        this.cuBtCode = cuBtCode;
    }

    public String getCuPterm() {
        return cuPterm;
    }

    public void setCuPterm(String cuPterm) {
        this.cuPterm = cuPterm;
    }

    public String getCuCcy() {
        return cuCcy;
    }

    public void setCuCcy(String cuCcy) {
        this.cuCcy = cuCcy;
    }

    public String getCuCrBal() {
        return cuCrBal;
    }

    public void setCuCrBal(String cuCrBal) {
        this.cuCrBal = cuCrBal;
    }

    public String getCuCrUsed() {
        return cuCrUsed;
    }

    public void setCuCrUsed(String cuCrUsed) {
        this.cuCrUsed = cuCrUsed;
    }

    public String getCuType() {
        return cuType;
    }

    public void setCuType(String cuType) {
        this.cuType = cuType;
    }

    public String getCuGlInfSi() {
        return cuGlInfSi;
    }

    public void setCuGlInfSi(String cuGlInfSi) {
        this.cuGlInfSi = cuGlInfSi;
    }

    public String getCuWebsiteUrl() {
        return cuWebsiteUrl;
    }

    public void setCuWebsiteUrl(String cuWebsiteUrl) {
        this.cuWebsiteUrl = cuWebsiteUrl;
    }

    public String getCuFSCCertCode() {
        return cuFSCCertCode;
    }

    public void setCuFSCCertCode(String cuFSCCertCode) {
        this.cuFSCCertCode = cuFSCCertCode;
    }

    public Date getCuFSCValidFm() {
        return cuFSCValidFm;
    }

    public void setCuFSCValidFm(Date cuFSCValidFm) {
        this.cuFSCValidFm = cuFSCValidFm;
    }

    public Date getCuFSCValidTo() {
        return cuFSCValidTo;
    }

    public void setCuFSCValidTo(Date cuFSCValidTo) {
        this.cuFSCValidTo = cuFSCValidTo;
    }

    public Map<String, CustAttn> getCustAttns() {
        return custAttns;
    }

    public void setCustAttns(Map<String, CustAttn> custAttns) {
        this.custAttns = custAttns;
    }
}
