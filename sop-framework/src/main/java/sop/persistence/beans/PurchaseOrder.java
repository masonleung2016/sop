package sop.persistence.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:58
 * @Package: sop.persistence.beans
 */


public class PurchaseOrder extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**
     * po_art_art_no
     */
    private String poArtArtNo;
    /**
     * po_art_ean_no
     */
    private String poArtEanNo;
    /**
     * po_art_general
     */
    private String poArtGeneral;
    /**
     * po_art_grn_point
     */
    private String poArtGrnPoint;
    /**
     * po_art_lang
     */
    private String poArtLang;
    /**
     * po_art_other
     */
    private String poArtOther;
    /**
     * po_art_resy
     */
    private String poArtResy;
    /**
     * po_cnf
     */
    private String poCnf;
    /**
     * po_cnf_port
     */
    private String poCnfPort;
    /**
     * po_cont_req
     */
    private String poContReq;
    /**
     * po_ctn_pkg_desc
     */
    private String poCtnPkgDesc;
    /**
     * po_curr
     */
    private String poCurr;
    /**
     * po_date
     */
    private Date poDate;
    /**
     * po_del_details
     */
    private String poDelDetails;
    /**
     * po_dep_date
     */
    private Date poDepDate;
    /**
     * po_dep_paid
     */
    private BigDecimal poDepPaid;
    /**
     * po_dep_ratio
     */
    private BigDecimal poDepRatio;
    /**
     * po_dest
     */
    private String poDest;
    /**
     * po_dtl_art_no
     */
    private String poDtlArtNo;
    /**
     * po_dtl_ean_no
     */
    private String poDtlEanNo;
    /**
     * po_dtl_general
     */
    private String poDtlGeneral;
    /**
     * po_dtl_grn_point
     */
    private String poDtlGrnPoint;
    /**
     * po_dtl_lang
     */
    private String poDtlLang;
    /**
     * po_dtl_other
     */
    private String poDtlOther;
    /**
     * po_dtl_resy
     */
    private String poDtlResy;
    /**
     * po_etd_date
     */
    private Date poEtdDate;
    /**
     * po_fob
     */
    private String poFob;
    /**
     * po_fob_port
     */
    private String poFobPort;
    /**
     * po_iart_ins_sh
     */
    private String poIartInsSh;
    /**
     * po_iart_label
     */
    private String poIartLabel;
    /**
     * po_iart_other
     */
    private String poIartOther;
    /**
     * po_idtl_ins_sh
     */
    private String poIdtlInsSh;
    /**
     * po_idtl_label
     */
    private String poIdtlLabel;
    /**
     * po_idtl_other
     */
    private String poIdtlOther;
    /**
     * po_ipkg_ins_sh
     */
    private String poIpkgInsSh;
    /**
     * po_ipkg_label
     */
    private String poIpkgLabel;
    /**
     * po_ipkg_other
     */
    private String poIpkgOther;
    /**
     * po_lshp_date
     */
    private Date poLshpDate;
    /**
     * po_no
     */
    private String poNo;
    /**
     * po_odtl
     */
    private String poOdtl;
    /**
     * po_ord_amt_word
     */
    private String poOrdAmtWord;
    /**
     * po_ord_tot_amt
     */
    private BigDecimal poOrdTotAmt;
    /**
     * po_ord_tot_chg
     */
    private BigDecimal poOrdTotChg;
    /**
     * po_ord_tot_net
     */
    private BigDecimal poOrdTotNet;
    /**
     * po_pkg_art_no
     */
    private String poPkgArtNo;
    /**
     * po_pkg_ean_no
     */
    private String poPkgEanNo;
    /**
     * po_pkg_general
     */
    private String poPkgGeneral;
    /**
     * po_pkg_grn_point
     */
    private String poPkgGrnPoint;
    /**
     * po_pkg_lang
     */
    private String poPkgLang;
    /**
     * po_pkg_other
     */
    private String poPkgOther;
    /**
     * po_pkg_resy
     */
    private String poPkgResy;
    /**
     * po_pterm
     */
    private String poPterm;
    /**
     * po_pterm_days
     */
    private BigDecimal poPtermDays;
    /**
     * po_routing
     */
    private String poRouting;
    /**
     * po_so_no_ref
     */
    private String poSoNoRef;
    /**
     * po_sp_main_mark
     */
    private String poSpMainMark;
    /**
     * po_sp_side_mark
     */
    private String poSpSideMark;
    /**
     * po_su_code
     */
    private String poSuCode;
    /**
     * po_su_pterm
     */
    private String poSuPterm;
    private Integer id;

    public String getPoArtArtNo() {
        return poArtArtNo;
    }

    public void setPoArtArtNo(String poArtArtNo) {
        this.poArtArtNo = poArtArtNo;
    }

    public String getPoArtEanNo() {
        return poArtEanNo;
    }

    public void setPoArtEanNo(String poArtEanNo) {
        this.poArtEanNo = poArtEanNo;
    }

    public String getPoArtGeneral() {
        return poArtGeneral;
    }

    public void setPoArtGeneral(String poArtGeneral) {
        this.poArtGeneral = poArtGeneral;
    }

    public String getPoArtGrnPoint() {
        return poArtGrnPoint;
    }

    public void setPoArtGrnPoint(String poArtGrnPoint) {
        this.poArtGrnPoint = poArtGrnPoint;
    }

    public String getPoArtLang() {
        return poArtLang;
    }

    public void setPoArtLang(String poArtLang) {
        this.poArtLang = poArtLang;
    }

    public String getPoArtOther() {
        return poArtOther;
    }

    public void setPoArtOther(String poArtOther) {
        this.poArtOther = poArtOther;
    }

    public String getPoArtResy() {
        return poArtResy;
    }

    public void setPoArtResy(String poArtResy) {
        this.poArtResy = poArtResy;
    }

    public String getPoCnf() {
        return poCnf;
    }

    public void setPoCnf(String poCnf) {
        this.poCnf = poCnf;
    }

    public String getPoCnfPort() {
        return poCnfPort;
    }

    public void setPoCnfPort(String poCnfPort) {
        this.poCnfPort = poCnfPort;
    }

    public String getPoContReq() {
        return poContReq;
    }

    public void setPoContReq(String poContReq) {
        this.poContReq = poContReq;
    }

    public String getPoCtnPkgDesc() {
        return poCtnPkgDesc;
    }

    public void setPoCtnPkgDesc(String poCtnPkgDesc) {
        this.poCtnPkgDesc = poCtnPkgDesc;
    }

    public String getPoCurr() {
        return poCurr;
    }

    public void setPoCurr(String poCurr) {
        this.poCurr = poCurr;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public String getPoDelDetails() {
        return poDelDetails;
    }

    public void setPoDelDetails(String poDelDetails) {
        this.poDelDetails = poDelDetails;
    }

    public Date getPoDepDate() {
        return poDepDate;
    }

    public void setPoDepDate(Date poDepDate) {
        this.poDepDate = poDepDate;
    }

    public BigDecimal getPoDepPaid() {
        return poDepPaid;
    }

    public void setPoDepPaid(BigDecimal poDepPaid) {
        this.poDepPaid = poDepPaid;
    }

    public BigDecimal getPoDepRatio() {
        return poDepRatio;
    }

    public void setPoDepRatio(BigDecimal poDepRatio) {
        this.poDepRatio = poDepRatio;
    }

    public String getPoDest() {
        return poDest;
    }

    public void setPoDest(String poDest) {
        this.poDest = poDest;
    }

    public String getPoDtlArtNo() {
        return poDtlArtNo;
    }

    public void setPoDtlArtNo(String poDtlArtNo) {
        this.poDtlArtNo = poDtlArtNo;
    }

    public String getPoDtlEanNo() {
        return poDtlEanNo;
    }

    public void setPoDtlEanNo(String poDtlEanNo) {
        this.poDtlEanNo = poDtlEanNo;
    }

    public String getPoDtlGeneral() {
        return poDtlGeneral;
    }

    public void setPoDtlGeneral(String poDtlGeneral) {
        this.poDtlGeneral = poDtlGeneral;
    }

    public String getPoDtlGrnPoint() {
        return poDtlGrnPoint;
    }

    public void setPoDtlGrnPoint(String poDtlGrnPoint) {
        this.poDtlGrnPoint = poDtlGrnPoint;
    }

    public String getPoDtlLang() {
        return poDtlLang;
    }

    public void setPoDtlLang(String poDtlLang) {
        this.poDtlLang = poDtlLang;
    }

    public String getPoDtlOther() {
        return poDtlOther;
    }

    public void setPoDtlOther(String poDtlOther) {
        this.poDtlOther = poDtlOther;
    }

    public String getPoDtlResy() {
        return poDtlResy;
    }

    public void setPoDtlResy(String poDtlResy) {
        this.poDtlResy = poDtlResy;
    }

    public Date getPoEtdDate() {
        return poEtdDate;
    }

    public void setPoEtdDate(Date poEtdDate) {
        this.poEtdDate = poEtdDate;
    }

    public String getPoFob() {
        return poFob;
    }

    public void setPoFob(String poFob) {
        this.poFob = poFob;
    }

    public String getPoFobPort() {
        return poFobPort;
    }

    public void setPoFobPort(String poFobPort) {
        this.poFobPort = poFobPort;
    }

    public String getPoIartInsSh() {
        return poIartInsSh;
    }

    public void setPoIartInsSh(String poIartInsSh) {
        this.poIartInsSh = poIartInsSh;
    }

    public String getPoIartLabel() {
        return poIartLabel;
    }

    public void setPoIartLabel(String poIartLabel) {
        this.poIartLabel = poIartLabel;
    }

    public String getPoIartOther() {
        return poIartOther;
    }

    public void setPoIartOther(String poIartOther) {
        this.poIartOther = poIartOther;
    }

    public String getPoIdtlInsSh() {
        return poIdtlInsSh;
    }

    public void setPoIdtlInsSh(String poIdtlInsSh) {
        this.poIdtlInsSh = poIdtlInsSh;
    }

    public String getPoIdtlLabel() {
        return poIdtlLabel;
    }

    public void setPoIdtlLabel(String poIdtlLabel) {
        this.poIdtlLabel = poIdtlLabel;
    }

    public String getPoIdtlOther() {
        return poIdtlOther;
    }

    public void setPoIdtlOther(String poIdtlOther) {
        this.poIdtlOther = poIdtlOther;
    }

    public String getPoIpkgInsSh() {
        return poIpkgInsSh;
    }

    public void setPoIpkgInsSh(String poIpkgInsSh) {
        this.poIpkgInsSh = poIpkgInsSh;
    }

    public String getPoIpkgLabel() {
        return poIpkgLabel;
    }

    public void setPoIpkgLabel(String poIpkgLabel) {
        this.poIpkgLabel = poIpkgLabel;
    }

    public String getPoIpkgOther() {
        return poIpkgOther;
    }

    public void setPoIpkgOther(String poIpkgOther) {
        this.poIpkgOther = poIpkgOther;
    }

    public Date getPoLshpDate() {
        return poLshpDate;
    }

    public void setPoLshpDate(Date poLshpDate) {
        this.poLshpDate = poLshpDate;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getPoOdtl() {
        return poOdtl;
    }

    public void setPoOdtl(String poOdtl) {
        this.poOdtl = poOdtl;
    }

    public String getPoOrdAmtWord() {
        return poOrdAmtWord;
    }

    public void setPoOrdAmtWord(String poOrdAmtWord) {
        this.poOrdAmtWord = poOrdAmtWord;
    }

    public BigDecimal getPoOrdTotAmt() {
        return poOrdTotAmt;
    }

    public void setPoOrdTotAmt(BigDecimal poOrdTotAmt) {
        this.poOrdTotAmt = poOrdTotAmt;
    }

    public BigDecimal getPoOrdTotChg() {
        return poOrdTotChg;
    }

    public void setPoOrdTotChg(BigDecimal poOrdTotChg) {
        this.poOrdTotChg = poOrdTotChg;
    }

    public BigDecimal getPoOrdTotNet() {
        return poOrdTotNet;
    }

    public void setPoOrdTotNet(BigDecimal poOrdTotNet) {
        this.poOrdTotNet = poOrdTotNet;
    }

    public String getPoPkgArtNo() {
        return poPkgArtNo;
    }

    public void setPoPkgArtNo(String poPkgArtNo) {
        this.poPkgArtNo = poPkgArtNo;
    }

    public String getPoPkgEanNo() {
        return poPkgEanNo;
    }

    public void setPoPkgEanNo(String poPkgEanNo) {
        this.poPkgEanNo = poPkgEanNo;
    }

    public String getPoPkgGeneral() {
        return poPkgGeneral;
    }

    public void setPoPkgGeneral(String poPkgGeneral) {
        this.poPkgGeneral = poPkgGeneral;
    }

    public String getPoPkgGrnPoint() {
        return poPkgGrnPoint;
    }

    public void setPoPkgGrnPoint(String poPkgGrnPoint) {
        this.poPkgGrnPoint = poPkgGrnPoint;
    }

    public String getPoPkgLang() {
        return poPkgLang;
    }

    public void setPoPkgLang(String poPkgLang) {
        this.poPkgLang = poPkgLang;
    }

    public String getPoPkgOther() {
        return poPkgOther;
    }

    public void setPoPkgOther(String poPkgOther) {
        this.poPkgOther = poPkgOther;
    }

    public String getPoPkgResy() {
        return poPkgResy;
    }

    public void setPoPkgResy(String poPkgResy) {
        this.poPkgResy = poPkgResy;
    }

    public String getPoPterm() {
        return poPterm;
    }

    public void setPoPterm(String poPterm) {
        this.poPterm = poPterm;
    }

    public BigDecimal getPoPtermDays() {
        return poPtermDays;
    }

    public void setPoPtermDays(BigDecimal poPtermDays) {
        this.poPtermDays = poPtermDays;
    }

    public String getPoRouting() {
        return poRouting;
    }

    public void setPoRouting(String poRouting) {
        this.poRouting = poRouting;
    }

    public String getPoSoNoRef() {
        return poSoNoRef;
    }

    public void setPoSoNoRef(String poSoNoRef) {
        this.poSoNoRef = poSoNoRef;
    }

    public String getPoSpMainMark() {
        return poSpMainMark;
    }

    public void setPoSpMainMark(String poSpMainMark) {
        this.poSpMainMark = poSpMainMark;
    }

    public String getPoSpSideMark() {
        return poSpSideMark;
    }

    public void setPoSpSideMark(String poSpSideMark) {
        this.poSpSideMark = poSpSideMark;
    }

    public String getPoSuCode() {
        return poSuCode;
    }

    public void setPoSuCode(String poSuCode) {
        this.poSuCode = poSuCode;
    }

    public String getPoSuPterm() {
        return poSuPterm;
    }

    public void setPoSuPterm(String poSuPterm) {
        this.poSuPterm = poSuPterm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
