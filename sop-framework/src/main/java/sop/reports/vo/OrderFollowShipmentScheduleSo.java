package sop.reports.vo;

import java.math.BigDecimal;
import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:21
 * @Package: sop.reports.vo
 */

public class OrderFollowShipmentScheduleSo extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private String co_code;
    
    private String so_no;
    
    private String so_cu_name;
    
    private String so_su_name;
    
    private String it_cat_no_suffix;
    
    private String so2_it_name;
    
    private String so_cont_req;
    
    private BigDecimal so2_ord_qty;
    
    private String so2_uom;
    
    private BigDecimal so2_uprice;
    
    private String so_fob_port;
    
    private Date so_lshp_date;
    
    private String so_handle;

    public OrderFollowShipmentScheduleSo() {

    }

    public String getCo_code() {
        return co_code;
    }

    public void setCo_code(String co_code) {
        this.co_code = co_code;
    }

    public String getSo_no() {
        return so_no;
    }

    public void setSo_no(String so_no) {
        this.so_no = so_no;
    }

    public String getSo_cu_name() {
        return so_cu_name;
    }

    public void setSo_cu_name(String so_cu_name) {
        this.so_cu_name = so_cu_name;
    }

    public String getSo_su_name() {
        return so_su_name;
    }

    public void setSo_su_name(String so_su_name) {
        this.so_su_name = so_su_name;
    }

    public String getIt_cat_no_suffix() {
        return it_cat_no_suffix;
    }

    public void setIt_cat_no_suffix(String it_cat_no_suffix) {
        this.it_cat_no_suffix = it_cat_no_suffix;
    }

    public String getSo2_it_name() {
        return so2_it_name;
    }

    public void setSo2_it_name(String so2_it_name) {
        this.so2_it_name = so2_it_name;
    }

    public String getSo_cont_req() {
        return so_cont_req;
    }

    public void setSo_cont_req(String so_cont_req) {
        this.so_cont_req = so_cont_req;
    }

    public BigDecimal getSo2_ord_qty() {
        return so2_ord_qty;
    }

    public void setSo2_ord_qty(BigDecimal so2_ord_qty) {
        this.so2_ord_qty = so2_ord_qty;
    }

    public String getSo2_uom() {
        return so2_uom;
    }

    public void setSo2_uom(String so2_uom) {
        this.so2_uom = so2_uom;
    }

    public BigDecimal getSo2_uprice() {
        return so2_uprice;
    }

    public void setSo2_uprice(BigDecimal so2_uprice) {
        this.so2_uprice = so2_uprice;
    }

    public String getSo_fob_port() {
        return so_fob_port;
    }

    public void setSo_fob_port(String so_fob_port) {
        this.so_fob_port = so_fob_port;
    }

    public Date getSo_lshp_date() {
        return so_lshp_date;
    }

    public void setSo_lshp_date(Date so_lshp_date) {
        this.so_lshp_date = so_lshp_date;
    }

    public String getSo_handle() {
        return so_handle;
    }

    public void setSo_handle(String so_handle) {
        this.so_handle = so_handle;
    }

    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }
}
