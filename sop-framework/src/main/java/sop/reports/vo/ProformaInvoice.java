package sop.reports.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:23
 * @Package: sop.reports.vo
 */


public class ProformaInvoice {
    private String customer;
    private String cuadd;
    private String attn;
    private String orderno;
    private String yourno;
    private String dest;
    private String fob;
    private String curr;
    private String itno;
    private String art;
    private String ean;
    private String itname;
    private String decription;
    private BigDecimal qty;
    private String uom;
    private BigDecimal so2_uprice;
    private BigDecimal so2_amt;
    private String remarks;
    private String container;
    private String shipment;
    private String terms;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCuadd() {
        return cuadd;
    }

    public void setCuadd(String cuadd) {
        this.cuadd = cuadd;
    }

    public String getAttn() {
        return attn;
    }

    public void setAttn(String attn) {
        this.attn = attn;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getYourno() {
        return yourno;
    }

    public void setYourno(String yourno) {
        this.yourno = yourno;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getFob() {
        return fob;
    }

    public void setFob(String fob) {
        this.fob = fob;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getItno() {
        return itno;
    }

    public void setItno(String itno) {
        this.itno = itno;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getItname() {
        return itname;
    }

    public void setItname(String itname) {
        this.itname = itname;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getSo2_uprice() {
        return so2_uprice;
    }

    public void setSo2_uprice(BigDecimal so2_uprice) {
        this.so2_uprice = so2_uprice;
    }

    public BigDecimal getSo2_amt() {
        return so2_amt;
    }

    public void setSo2_amt(BigDecimal so2_amt) {
        this.so2_amt = so2_amt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

}
