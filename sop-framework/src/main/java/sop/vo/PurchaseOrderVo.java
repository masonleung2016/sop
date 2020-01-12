package sop.vo;

import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:51
 * @Package: sop.vo
 */


public class PurchaseOrderVo {

    private String poNo;

    private String encodePoNo;

    private String poSoNoRef;

    private Date poDate;

    private Date poLshpDate;

    private Date poEtdDate;

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
        this.encodePoNo = Base64.encode(poNo, "utf-8");
    }

    public String getEncodePoNo() {
        return encodePoNo;
    }

    public void setEncodePoNo(String encodePoNo) {
        this.encodePoNo = encodePoNo;
    }

    public String getPoSoNoRef() {
        return poSoNoRef;
    }

    public void setPoSoNoRef(String poSoNoRef) {
        this.poSoNoRef = poSoNoRef;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public Date getPoLshpDate() {
        return poLshpDate;
    }

    public void setPoLshpDate(Date poLshpDate) {
        this.poLshpDate = poLshpDate;
    }

    public Date getPoEtdDate() {
        return poEtdDate;
    }

    public void setPoEtdDate(Date poEtdDate) {
        this.poEtdDate = poEtdDate;
    }
}
