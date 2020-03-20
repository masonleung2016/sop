package sop.reports.vo;

import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:20
 * @Package: sop.reports.vo
 */

public class OffAbCostPriceHead {

    private static final long serialVersionUID = 1L;

    private String offShNoPfixNo;
    
    private String coName;
    
    private String cuName;
    
    private String offCustAttn;
    
    private String offShNoPfix;
    
    private String offShNo;
    
    private Date offDate;

    public OffAbCostPriceHead() {

    }

    public String getOffShNoPfixNo() {
        return offShNoPfixNo;
    }

    public void setOffShNoPfixNo(String offShNoPfixNo) {
        this.offShNoPfixNo = offShNoPfixNo;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getOffCustAttn() {
        return offCustAttn;
    }

    public void setOffCustAttn(String offCustAttn) {
        this.offCustAttn = offCustAttn;
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
}
