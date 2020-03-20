package sop.reports.vo;

import java.math.BigDecimal;
import java.util.Date;

import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:22
 * @Package: sop.reports.vo
 */

public class PoShipmentSchedule extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private String co_code;
    
    private String po_no;
    
    private String po_su_code;
    
    private String so_su_name;
    
    private String it_cat_no_suffix;
    
    private String po2_it_name;
    
    private String po_cont_req;
    
    private BigDecimal po2_ord_qty;
    
    private BigDecimal po2_shd_qty;
    
    private BigDecimal po2_os_tot_shd_qty;
    
    private String po2_uom;
    
    private BigDecimal po2_uprice;
    
    private String po_routing;
    
    private BigDecimal po2_amt;
    
    private Date po_etd_date;
    
    private Date po_lshp_date;
    
    private String po_fob_port;

    public PoShipmentSchedule() {

    }

    public String getCo_code() {
        return co_code;
    }

    public void setCo_code(String co_code) {
        this.co_code = co_code;
    }

    public String getPo_no() {
        return po_no;
    }

    public void setPo_no(String po_no) {
        this.po_no = po_no;
    }

    public String getPo_su_code() {
        return po_su_code;
    }

    public void setPo_su_code(String po_su_code) {
        this.po_su_code = po_su_code;
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

    public String getPo2_it_name() {
        return po2_it_name;
    }

    public void setPo2_it_name(String po2_it_name) {
        this.po2_it_name = po2_it_name;
    }

    public String getPo_cont_req() {
        return po_cont_req;
    }

    public void setPo_cont_req(String po_cont_req) {
        this.po_cont_req = po_cont_req;
    }

    public BigDecimal getPo2_ord_qty() {
        return po2_ord_qty;
    }

    public void setPo2_ord_qty(BigDecimal po2_ord_qty) {
        this.po2_ord_qty = po2_ord_qty;
    }

    public BigDecimal getPo2_shd_qty() {
        return po2_shd_qty;
    }

    public void setPo2_shd_qty(BigDecimal po2_shd_qty) {
        this.po2_shd_qty = po2_shd_qty;
        this.po2_os_tot_shd_qty = this.po2_ord_qty.subtract(po2_shd_qty);
    }

    public String getPo2_uom() {
        return po2_uom;
    }

    public void setPo2_uom(String po2_uom) {
        this.po2_uom = po2_uom;
    }

    public BigDecimal getPo2_uprice() {
        return po2_uprice;
    }

    public void setPo2_uprice(BigDecimal po2_uprice) {
        this.po2_uprice = po2_uprice;
    }

    public String getPo_routing() {
        return po_routing;
    }

    public void setPo_routing(String po_routing) {
        this.po_routing = po_routing;
    }

    public BigDecimal getPo2_amt() {
        return po2_amt;
    }

    public void setPo2_amt(BigDecimal po2_amt) {
        this.po2_amt = po2_amt;
    }

    public Date getPo_etd_date() {
        return po_etd_date;
    }

    public void setPo_etd_date(Date po_etd_date) {
        this.po_etd_date = po_etd_date;
    }

    public Date getPo_lshp_date() {
        return po_lshp_date;
    }

    public void setPo_lshp_date(Date po_lshp_date) {
        this.po_lshp_date = po_lshp_date;
    }

    public String getPo_fob_port() {
        return po_fob_port;
    }

    public void setPo_fob_port(String po_fob_port) {
        this.po_fob_port = po_fob_port;
    }

    public BigDecimal getPo2_os_tot_shd_qty() {
        return po2_os_tot_shd_qty;
    }

    public void setPo2_os_tot_shd_qty(BigDecimal po2_os_tot_shd_qty) {
        this.po2_os_tot_shd_qty = po2_os_tot_shd_qty;
    }

    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }
}
