package sop.reports.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:12
 * @Package: sop.reports.vo
 */


public class CostPriceList {
    private String no;
    private String date;
    private String vto;
    private String attn;
    private String itno;
    private String itname;
    private BigDecimal it_pkg_20_qty;
    private BigDecimal it_pkg_40_qty;
    private BigDecimal it_fty_fob_cost;
    private String it_fty_fob_port;
    private String it_image_1;
    private String it_fty_code;
    private String it_fty_name;
    private String it_fty_item_no;

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

    public BigDecimal getIt_fty_fob_cost() {
        return it_fty_fob_cost;
    }

    public void setIt_fty_fob_cost(BigDecimal it_fty_fob_cost) {
        this.it_fty_fob_cost = it_fty_fob_cost;
    }

    public String getIt_fty_fob_port() {
        return it_fty_fob_port;
    }

    public void setIt_fty_fob_port(String it_fty_fob_port) {
        this.it_fty_fob_port = it_fty_fob_port;
    }

    public String getIt_image_1() {
        return it_image_1;
    }

    public void setIt_image_1(String it_image_1) {
        this.it_image_1 = it_image_1;
    }

    public String getIt_fty_code() {
        return it_fty_code;
    }

    public void setIt_fty_code(String it_fty_code) {
        this.it_fty_code = it_fty_code;
    }

    public String getIt_fty_name() {
        return it_fty_name;
    }

    public void setIt_fty_name(String it_fty_name) {
        this.it_fty_name = it_fty_name;
    }

    public String getIt_fty_item_no() {
        return it_fty_item_no;
    }

    public void setIt_fty_item_no(String it_fty_item_no) {
        this.it_fty_item_no = it_fty_item_no;
    }

}
