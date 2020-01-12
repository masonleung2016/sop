package sop.reports.vo;

import java.math.BigDecimal;
import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:19
 * @Package: sop.reports.vo
 */


public class OffAbCostPrice extends AbstractDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String off_sh_no_pfix_no;

    private String cu_name;

    private String off_cust_attn;

    private String off_sh_no_pfix;

    private String off_sh_no;

    private Date off_date;

    private String it_cat_no_suffix;

    private String off2_it_name;

    private BigDecimal it_pkg_20_qty;

    private BigDecimal it_pkg_40_qty;

    private String off2_curr_code;

    private BigDecimal off2_fob_price;

    private BigDecimal off2_fob_price_b;

    private BigDecimal off2_fob_cost;

    private BigDecimal off2_fob_cost_b;

    private String off2_fob_port;

    private Date it_fty_cost_last_update;

    private String it_fty_item_no;

    private String it_fty_code;

    private String it_fty_name;

    private String it_image_1;

    public OffAbCostPrice() {

    }

    public String getOff_sh_no_pfix_no() {
        return off_sh_no_pfix_no;
    }

    public void setOff_sh_no_pfix_no(String off_sh_no_pfix_no) {
        this.off_sh_no_pfix_no = off_sh_no_pfix_no;
    }

    public String getCu_name() {
        return cu_name;
    }

    public void setCu_name(String cu_name) {
        this.cu_name = cu_name;
    }

    public String getOff_cust_attn() {
        return off_cust_attn;
    }

    public void setOff_cust_attn(String off_cust_attn) {
        this.off_cust_attn = off_cust_attn;
    }

    public String getOff_sh_no_pfix() {
        return off_sh_no_pfix;
    }

    public void setOff_sh_no_pfix(String off_sh_no_pfix) {
        this.off_sh_no_pfix = off_sh_no_pfix;
    }

    public String getOff_sh_no() {
        return off_sh_no;
    }

    public void setOff_sh_no(String off_sh_no) {
        this.off_sh_no = off_sh_no;
    }

    public Date getOff_date() {
        return off_date;
    }

    public void setOff_date(Date off_date) {
        this.off_date = off_date;
    }

    public String getIt_cat_no_suffix() {
        return it_cat_no_suffix;
    }

    public void setIt_cat_no_suffix(String it_cat_no_suffix) {
        this.it_cat_no_suffix = it_cat_no_suffix;
    }

    public String getOff2_it_name() {
        return off2_it_name;
    }

    public void setOff2_it_name(String off2_it_name) {
        this.off2_it_name = off2_it_name;
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

    public String getOff2_curr_code() {
        return off2_curr_code;
    }

    public void setOff2_curr_code(String off2_curr_code) {
        this.off2_curr_code = off2_curr_code;
    }

    public BigDecimal getOff2_fob_price() {
        return off2_fob_price;
    }

    public void setOff2_fob_price(BigDecimal off2_fob_price) {
        this.off2_fob_price = off2_fob_price;
    }

    public BigDecimal getOff2_fob_price_b() {
        return off2_fob_price_b;
    }

    public void setOff2_fob_price_b(BigDecimal off2_fob_price_b) {
        this.off2_fob_price_b = off2_fob_price_b;
    }

    public BigDecimal getOff2_fob_cost() {
        return off2_fob_cost;
    }

    public void setOff2_fob_cost(BigDecimal off2_fob_cost) {
        this.off2_fob_cost = off2_fob_cost;
    }

    public BigDecimal getOff2_fob_cost_b() {
        return off2_fob_cost_b;
    }

    public void setOff2_fob_cost_b(BigDecimal off2_fob_cost_b) {
        this.off2_fob_cost_b = off2_fob_cost_b;
    }

    public String getOff2_fob_port() {
        return off2_fob_port;
    }

    public void setOff2_fob_port(String off2_fob_port) {
        this.off2_fob_port = off2_fob_port;
    }

    public Date getIt_fty_cost_last_update() {
        return it_fty_cost_last_update;
    }

    public void setIt_fty_cost_last_update(Date it_fty_cost_last_update) {
        this.it_fty_cost_last_update = it_fty_cost_last_update;
    }

    public String getIt_fty_item_no() {
        return it_fty_item_no;
    }

    public void setIt_fty_item_no(String it_fty_item_no) {
        this.it_fty_item_no = it_fty_item_no;
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

    public String getIt_image_1() {
        return it_image_1;
    }

    public void setIt_image_1(String it_image_1) {
        this.it_image_1 = it_image_1;
    }

    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }
}
