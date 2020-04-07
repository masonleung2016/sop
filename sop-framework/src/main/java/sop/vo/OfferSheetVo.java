package sop.vo;

import java.util.Date;

import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.BaseBean;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:43
 * @Package: sop.vo
 */

public class OfferSheetVo extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * co_code
     */
    private String coCode;
    /**
     * off_sh_no_pfix
     */
    private String offShNoPfix;
    /**
     * off_sh_no
     */
    private String offShNo;
    /**
     * off_sh_no_pfix_no
     */
    private String offShNoPfixNo;

    private String encodeOffShNoPfixNo;
    /**
     * off_date
     */
    private Date offDate;
    /**
     * off_cust
     */
    private String offCust;
    /**
     * off_cust_attn
     */
    private String offCustAttn;

    public OfferSheetVo() {
    }

    public OfferSheetVo(String coCode, String offShNoPfix, String offShNo) {
        this.coCode = coCode;
        this.offShNoPfix = offShNoPfix;
        this.offShNo = offShNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getOffShNoPfix() {
        return offShNoPfix;
    }

    public void setOffShNoPfix(String offShNoPfix) {
        this.offShNoPfix = offShNoPfix;
    }

    public String getOffShNo() {
        return offShNo;
    }

    public void setOffShNo(String offShNo) {
        this.offShNo = offShNo;
    }

    public Date getOffDate() {
        return offDate;
    }

    public void setOffDate(Date offDate) {
        this.offDate = offDate;
    }

    public String getOffCust() {
        return offCust;
    }

    public void setOffCust(String offCust) {
        this.offCust = offCust;
    }

    public String getOffCustAttn() {
        return offCustAttn;
    }

    public void setOffCustAttn(String offCustAttn) {
        this.offCustAttn = offCustAttn;
    }

    public String getOffShNoPfixNo() {
        return offShNoPfixNo;
    }

    public void setOffShNoPfixNo(String offShNoPfixNo) {
        this.offShNoPfixNo = offShNoPfixNo;
        this.encodeOffShNoPfixNo = Base64.encode(offShNoPfixNo, "utf-8");
    }

    public String getEncodeOffShNoPfixNo() {
        return encodeOffShNoPfixNo;
    }
}
