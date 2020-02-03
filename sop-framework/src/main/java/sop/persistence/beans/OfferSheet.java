package sop.persistence.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import sop.vo.OfferSheetVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:51
 * @Package: sop.persistence.beans
 */

public class OfferSheet extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String coCode;

    private String offShNoPfix;

    private String offShNo;

    private String offShNoPfixNo;

    private Date offDate;

    private String offCust;

    private String offCustAttn;

    private List<Item> items;

    private Map<String, OfferSheetItem> offerSheetItems;

    public OfferSheet() {

    }

    public OfferSheet(OfferSheetVo offerSheetVo) {
        this.coCode = offerSheetVo.getCoCode();
        this.offShNo = offerSheetVo.getOffShNo();
        this.offShNoPfix = offerSheetVo.getOffShNoPfix();
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

    public Map<String, OfferSheetItem> getOfferSheetItems() {
        return offerSheetItems;
    }

    public void setOfferSheetItems(Map<String, OfferSheetItem> offerSheetItems) {
        this.offerSheetItems = offerSheetItems;
    }

    public String getOffShNoPfixNo() {
        return offShNoPfixNo;
    }

    public void setOffShNoPfixNo(String offShNoPfixNo) {
        this.offShNoPfixNo = offShNoPfixNo;
    }
}
