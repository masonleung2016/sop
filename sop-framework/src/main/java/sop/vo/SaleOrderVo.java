package sop.vo;

import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:57
 * @Package: sop.vo
 */


public class SaleOrderVo {

    private String soNo;

    private String encodeSoNo;

    private String soCuCode;

    private String soCuPoNo;

    private Date soDate;

    private Date soEtd;

    private Date soLshpDate;

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
        this.encodeSoNo = Base64.encode(soNo, "utf-8");
    }

    public String getSoCuCode() {
        return soCuCode;
    }

    public void setSoCuCode(String soCuCode) {
        this.soCuCode = soCuCode;
    }

    public String getEncodeSoNo() {
        return encodeSoNo;
    }

    public void setEncodeSoNo(String encodeSoNo) {
        this.encodeSoNo = encodeSoNo;
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

    public Date getSoEtd() {
        return soEtd;
    }

    public void setSoEtd(Date soEtd) {
        this.soEtd = soEtd;
    }

    public Date getSoLshpDate() {
        return soLshpDate;
    }

    public void setSoLshpDate(Date soLshpDate) {
        this.soLshpDate = soLshpDate;
    }
}
