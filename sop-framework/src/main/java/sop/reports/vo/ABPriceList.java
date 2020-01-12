package sop.reports.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:12
 * @Package: sop.reports.vo
 */


public class ABPriceList {
    private String no;
    private String date;
    private String vto;
    private String attn;
    private String itno;
    private String itname;
    private BigDecimal it_pkg_20_qty;
    private BigDecimal it_pkg_40_qty;
    private String it_image_1;
    private String off2_fob_port;
    private BigDecimal price;
    private BigDecimal priceb;
    private String off2_curr_code;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVto() {
        return vto;
    }

    public void setVto(String vto) {
        this.vto = vto;
    }

    public String getAttn() {
        return attn;
    }

    public void setAttn(String attn) {
        this.attn = attn;
    }

    public String getItno() {
        return itno;
    }

    public void setItno(String itno) {
        this.itno = itno;
    }

    public String getItname() {
        return itname;
    }

    public void setItname(String itname) {
        this.itname = itname;
    }

    public BigDecimal getIt_pkg_20_qty() {
        return it_pkg_20_qty;
    }

    public void setIt_pkg_20_qty(BigDecimal it_pkg_20_qty) {
        this.it_pkg_20_qty = it_pkg_20_qty;
    }

    public BigDecimal getIt_pkg_40_qty() {
        return it_pkg_40_qty;
    }

    public void setIt_pkg_40_qty(BigDecimal it_pkg_40_qty) {
        this.it_pkg_40_qty = it_pkg_40_qty;
    }

    public String getIt_image_1() {
        return it_image_1;
    }

    public void setIt_image_1(String it_image_1) {
        this.it_image_1 = it_image_1;
    }

    public String getOff2_fob_port() {
        return off2_fob_port;
    }

    public void setOff2_fob_port(String off2_fob_port) {
        this.off2_fob_port = off2_fob_port;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceb() {
        return priceb;
    }

    public void setPriceb(BigDecimal priceb) {
        this.priceb = priceb;
    }

    public String getOff2_curr_code() {
        return off2_curr_code;
    }

    public void setOff2_curr_code(String off2_curr_code) {
        this.off2_curr_code = off2_curr_code;
    }

}
