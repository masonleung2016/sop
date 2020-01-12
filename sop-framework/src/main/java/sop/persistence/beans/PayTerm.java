package sop.persistence.beans;

import java.math.BigDecimal;

/**
 * pay_term表对应的实体类
 *
 * @Author: LCF
 * @Date: 2020/1/9 9:55
 * @Package: sop.persistence.beans
 */
public class PayTerm extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 571494775875473675L;
    //实体类的属性和表的字段名称一一对应
    private Integer id;
    /**
     * pay_code
     */
    private String payCode;
    /**
     * pay_desc
     */
    private String payDesc;
    /**
     * pay_days
     */
    private BigDecimal payDays;
    /**
     * pay_date
     */
    private Double payDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayDesc() {
        return payDesc;
    }

    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    public BigDecimal getPayDays() {
        return payDays;
    }

    public void setPayDays(BigDecimal payDays) {
        this.payDays = payDays;
    }

    public Double getPayDate() {
        return payDate;
    }

    public void setPayDate(Double payDate) {
        this.payDate = payDate;
    }
}
