package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:05
 * @Package: sop.persistence.beans
 */

public class SaleOrder extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String coCode;

    private String soNo;

    private String soCuCode;

    private String soCuPoNo;

    private Date soDate;

    private Date soLshpDate;

    private Date soEtd;

    private String soCurr;

    private String soFob;

    private String soFobPort;

    private String soCnf;

    private String soCnfPort;
 
    private String soSuCode;

    private String soContReq;

    private String soDest;

    private String soRouting;

    private String soSuConNo;

    private String soSuPterm;

    private String soDelDetails;

    private String soPayTerm;

    private String soOdtlProf;

    private BigDecimal soOrdTotAmt;

    private String soOrdAmtWord;

    private BigDecimal soOrdTotChg;

    private BigDecimal soOrdTotNet;

    private String soHandle;

    private String soPaidStatus;

    private String soBnkElc;

    private String soBnkTf;

    private String soStatus;

    private String soPterm;

    private BigDecimal soPtermDays;

    private BigDecimal soDepPaid;

    private BigDecimal soDepRatio;

    private Date soDepDate;

    private String soDestFinal;

    private String soCuName;

    private String soSuName;

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

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getSoCuCode() {
        return soCuCode;
    }

    public void setSoCuCode(String soCuCode) {
        this.soCuCode = soCuCode;
    }

    public String getSoCuPoNo() {
        return soCuPoNo;
    }

    public void setSoCuPoNo(String soCuPoNo) {
        this.soCuPoNo = soCuPoNo;
    }

    public Date getSoDate() {
        return soDate;
    }

    public void setSoDate(Date soDate) {
        this.soDate = soDate;
    }

    public Date getSoLshpDate() {
        return soLshpDate;
    }

    public void setSoLshpDate(Date soLshpDate) {
        this.soLshpDate = soLshpDate;
    }

    public Date getSoEtd() {
        return soEtd;
    }

    public void setSoEtd(Date soEtd) {
        this.soEtd = soEtd;
    }

    public String getSoCurr() {
        return soCurr;
    }

    public void setSoCurr(String soCurr) {
        this.soCurr = soCurr;
    }

    public String getSoFob() {
        return soFob;
    }

    public void setSoFob(String soFob) {
        this.soFob = soFob;
    }

    public String getSoFobPort() {
        return soFobPort;
    }

    public void setSoFobPort(String soFobPort) {
        this.soFobPort = soFobPort;
    }

    public String getSoCnf() {
        return soCnf;
    }

    public void setSoCnf(String soCnf) {
        this.soCnf = soCnf;
    }

    public String getSoCnfPort() {
        return soCnfPort;
    }

    public void setSoCnfPort(String soCnfPort) {
        this.soCnfPort = soCnfPort;
    }

    public String getSoSuCode() {
        return soSuCode;
    }

    public void setSoSuCode(String soSuCode) {
        this.soSuCode = soSuCode;
    }

    public String getSoContReq() {
        return soContReq;
    }

    public void setSoContReq(String soContReq) {
        this.soContReq = soContReq;
    }

    public String getSoDest() {
        return soDest;
    }

    public void setSoDest(String soDest) {
        this.soDest = soDest;
    }

    public String getSoRouting() {
        return soRouting;
    }

    public void setSoRouting(String soRouting) {
        this.soRouting = soRouting;
    }

    public String getSoSuConNo() {
        return soSuConNo;
    }

    public void setSoSuConNo(String soSuConNo) {
        this.soSuConNo = soSuConNo;
    }

    public String getSoSuPterm() {
        return soSuPterm;
    }

    public void setSoSuPterm(String soSuPterm) {
        this.soSuPterm = soSuPterm;
    }

    public String getSoDelDetails() {
        return soDelDetails;
    }

    public void setSoDelDetails(String soDelDetails) {
        this.soDelDetails = soDelDetails;
    }

    public String getSoPayTerm() {
        return soPayTerm;
    }

    public void setSoPayTerm(String soPayTerm) {
        this.soPayTerm = soPayTerm;
    }

    public String getSoOdtlProf() {
        return soOdtlProf;
    }

    public void setSoOdtlProf(String soOdtlProf) {
        this.soOdtlProf = soOdtlProf;
    }

    public BigDecimal getSoOrdTotAmt() {
        return soOrdTotAmt;
    }

    public void setSoOrdTotAmt(BigDecimal soOrdTotAmt) {
        this.soOrdTotAmt = soOrdTotAmt;
    }

    public String getSoOrdAmtWord() {
        return soOrdAmtWord;
    }

    public void setSoOrdAmtWord(String soOrdAmtWord) {
        this.soOrdAmtWord = soOrdAmtWord;
    }

    public BigDecimal getSoOrdTotChg() {
        return soOrdTotChg;
    }

    public void setSoOrdTotChg(BigDecimal soOrdTotChg) {
        this.soOrdTotChg = soOrdTotChg;
    }

    public BigDecimal getSoOrdTotNet() {
        return soOrdTotNet;
    }

    public void setSoOrdTotNet(BigDecimal soOrdTotNet) {
        this.soOrdTotNet = soOrdTotNet;
    }

    public String getSoHandle() {
        return soHandle;
    }

    public void setSoHandle(String soHandle) {
        this.soHandle = soHandle;
    }

    public String getSoPaidStatus() {
        return soPaidStatus;
    }

    public void setSoPaidStatus(String soPaidStatus) {
        this.soPaidStatus = soPaidStatus;
    }

    public String getSoBnkElc() {
        return soBnkElc;
    }

    public void setSoBnkElc(String soBnkElc) {
        this.soBnkElc = soBnkElc;
    }

    public String getSoBnkTf() {
        return soBnkTf;
    }

    public void setSoBnkTf(String soBnkTf) {
        this.soBnkTf = soBnkTf;
    }

    public String getSoStatus() {
        return soStatus;
    }

    public void setSoStatus(String soStatus) {
        this.soStatus = soStatus;
    }

    public String getSoPterm() {
        return soPterm;
    }

    public void setSoPterm(String soPterm) {
        this.soPterm = soPterm;
    }

    public BigDecimal getSoPtermDays() {
        return soPtermDays;
    }

    public void setSoPtermDays(BigDecimal soPtermDays) {
        this.soPtermDays = soPtermDays;
    }

    public BigDecimal getSoDepPaid() {
        return soDepPaid;
    }

    public void setSoDepPaid(BigDecimal soDepPaid) {
        this.soDepPaid = soDepPaid;
    }

    public BigDecimal getSoDepRatio() {
        return soDepRatio;
    }

    public void setSoDepRatio(BigDecimal soDepRatio) {
        this.soDepRatio = soDepRatio;
    }

    public Date getSoDepDate() {
        return soDepDate;
    }

    public void setSoDepDate(Date soDepDate) {
        this.soDepDate = soDepDate;
    }

    public String getSoDestFinal() {
        return soDestFinal;
    }

    public void setSoDestFinal(String soDestFinal) {
        this.soDestFinal = soDestFinal;
    }

    public String getSoCuName() {
        return soCuName;
    }

    public void setSoCuName(String soCuName) {
        this.soCuName = soCuName;
    }

    public String getSoSuName() {
        return soSuName;
    }

    public void setSoSuName(String soSuName) {
        this.soSuName = soSuName;
    }
}
